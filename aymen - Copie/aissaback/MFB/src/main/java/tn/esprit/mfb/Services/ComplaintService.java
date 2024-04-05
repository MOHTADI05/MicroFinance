package tn.esprit.mfb.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.mfb.Repository.ComplaintRepository;
import tn.esprit.mfb.Repository.TaskRepository;
import tn.esprit.mfb.Repository.UserRepository;
import tn.esprit.mfb.entity.*;
import tn.esprit.mfb.exeption.ComplaintNotFoundException;
import tn.esprit.mfb.exeption.ComplaintServiceException;
import tn.esprit.mfb.serviceInterface.IComplaintService;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ComplaintService implements IComplaintService {

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private NotificationService notificationService;
    @Autowired
    private UserService userService;
    @Autowired
    private SmsService smsService;

    @Override
    @Secured({"ROLE_ADMIN", "ROLE_AGENT"})
    public List<Complaint> selectAll() {
        try {
            return complaintRepository.findAll();
        } catch (Exception e) {
            throw new ComplaintServiceException("Error while retrieving all complaints", e);
        }
    }

    @Override
    public Complaint selectById(Integer id) {
        return complaintRepository.findById(id)
                .orElseThrow(() -> new ComplaintNotFoundException("Complaint not found with id: " + id));
    }

    @Override
    public void upComplaintProgress(Integer complaintId) {
        Complaint complaint = complaintRepository.findById(complaintId).orElse(null);
        if (complaint == null) {
            return;
        }


        List<Task> tasks = taskRepository.findByComplaint_ComplaintId(complaintId);
        if (tasks.isEmpty()) {
            return;
        }

        double totalProgressTimesEffort = 0.0;

        for (Task task : tasks) {
            totalProgressTimesEffort += task.getTProgress() * task.getEffort();
        }

        double overallProgress = totalProgressTimesEffort / 100.0;

        overallProgress = Math.min(Math.max(overallProgress, 0.0), 100.0);

        if (overallProgress < 100 && overallProgress != complaint.getCProgress()) {
            complaint.setCProgress(overallProgress);
            complaintRepository.save(complaint);
        }

    }


    @Override
    @Secured({"ROLE_ADMIN", "ROLE_AGENT" , "ROLE_USER"})
    public Resource downloadFile(Integer complaintId) {
        Complaint complaint = getComplaintById(complaintId);
        byte[] fileBytes = complaint.getAttachComplaint();

        // Return the image as a resource
        return new ByteArrayResource(fileBytes);
    }

    @Override
    @PreAuthorize("#userId == authentication.principal.username")
    public List<Complaint> selectAllByUserId(Long userId) {
        return complaintRepository.findAllByUsercCin(userId);
    }

    @Override
    @Secured({"ROLE_ADMIN", "ROLE_AGENT"})
    public List<Complaint> selectAllByCategory(ComplaintCategories cat) {
        return complaintRepository.findAllByComplaintCategory(cat);
    }

    @Override
    @PreAuthorize("JWT")
    @Secured({ "ROLE_AGENT" , "ROLE_USER"})
    public void addNewComplaint(Long userIdEntry, Optional<MultipartFile> file, String desc, ComplaintCategories cat, ComplaintUrgencies urgency) {
        User user = userRepository.findById(userIdEntry)
                .orElseThrow(() -> new ComplaintServiceException("User not found with ID: " + userIdEntry));

        Complaint cl = new Complaint();
        cl.setUserc(user);
        cl.setComplaintUrgency(urgency);
        if (urgency.equals(ComplaintUrgencies.EXTREME)){
            userService.BlockUser(user.getCin());
            smsService.sendSms("+21655335154","HIGHRISK: ACCOUNT_BLOCKED");
        }

        file.ifPresent(f -> {
            String attachment = uploadFile(cl.getComplaintId(), f);
            cl.setAttachComplaint(attachment.getBytes());
        });

        cl.setComplaintCategory(cat);
        cl.setComplaintDescription(desc);
        cl.setComplaintStatus(ComplaintStatus.CREATED);
        cl.setComplaintDate(new Date());

        complaintRepository.save(cl);

        notificationService.createNotificationp(user, Optional.of(cl), "New Complaint", "A new complaint has been registered");
    }

    @Override
    public String uploadFile(Integer complaintId, MultipartFile file) {
        Complaint complaint = getComplaintById(complaintId);

        try {
            byte[] fileBytes = file.getBytes();
            complaint.setAttachComplaint(fileBytes);
            complaintRepository.save(complaint);
        } catch (IOException e) {
            throw new ComplaintServiceException("Failed to download file to complaint: " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public void saveComplaint(Complaint complaint) {
        complaintRepository.save(complaint);
    }



    @Override
    public Complaint getComplaintById(Integer complaintId) {
        return complaintRepository.findById(complaintId)
                .orElseThrow(() -> new ComplaintNotFoundException("Complaint not found with ID: " + complaintId));
    }

    @Override
    @Secured({"ROLE_ADMIN", "ROLE_AGENT"})
    public void answerComplaint(Integer complaintId, String complaintResponse, ComplaintStatus status) {
        Complaint existingComplaint = getComplaintById(complaintId);
        existingComplaint.setComplaintResponse(complaintResponse);
        existingComplaint.setComplaintStatus(status);
        existingComplaint.setComplaintResponseDate(new Date());
        complaintRepository.save(existingComplaint);
        notificationService.createNotificationp(existingComplaint.getUserc(), Optional.of(existingComplaint), "Complaint Answered", "Your complaint has been answered.");
    }

    @Override
    @Secured({"ROLE_ADMIN", "ROLE_AGENT" , "ROLE_USER"})
    public void cancelComplaint(Integer cId) {
        Complaint existingComplaint = getComplaintById(cId);
        existingComplaint.setComplaintStatus(ComplaintStatus.CANCELLED);
        existingComplaint.setComplaintResponseDate(new Date());
        complaintRepository.save(existingComplaint);
        notificationService.createNotificationp(existingComplaint.getUserc(), Optional.of(existingComplaint), "Complaint Cancelled", "Your complaint has been cancelled.");
    }

    @Override
    public void attachFile(Integer complaintId, MultipartFile file) {
        Complaint complaint = getComplaintById(complaintId);
        try {
            byte[] fileBytes = file.getBytes();
            complaint.setAttachComplaint(fileBytes);
            complaintRepository.save(complaint);
        } catch (IOException e) {
            throw new ComplaintServiceException("Failed to attach file to complaint: " + e.getMessage(), e);
        }
    }


    @Override
    @Secured({"ROLE_ADMIN", "ROLE_AGENT"})
    public List<Complaint> searchByCategory(ComplaintCategories cat) {
        return complaintRepository.findByComplaintCategory(cat);
    }

    @Override
    @Secured({"ROLE_ADMIN", "ROLE_AGENT"})
    public List<Complaint> searchByDate(Date q) {
        return complaintRepository.findByComplaintDate(q);
    }

    @Override
    public void updateComplaintStatusIfAllTasksCompleted(Complaint complaint) {
        List<Task> tasks = taskRepository.findByComplaint(complaint);
        boolean allCompleted = tasks.stream()
                .allMatch(task -> task.getStatus() == TaskStatus.COMPLETED);
        if (allCompleted) {
            complaint.setComplaintStatus(ComplaintStatus.VERIFIED);
            complaintRepository.save(complaint);
        }
    }
}
