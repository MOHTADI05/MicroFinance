package tn.esprit.mfb.serviceInterface;

import org.springframework.core.io.Resource;
import org.springframework.security.access.annotation.Secured;
import tn.esprit.mfb.entity.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface IComplaintService {

    void upComplaintProgress(Integer complaintId);

    @Secured({"ROLE_ADMIN", "ROLE_AGENT" , "ROLE_USER"})
    Resource downloadFile(Integer complaintId);

    //FOR USER: only executed if userId input matches the userId of the user executing
    //For admin and agent: no constraints to use
    List<Complaint> selectAllByUserId(Long userId);



    List<Complaint> selectAllByCategory(ComplaintCategories cat);


    //No constraints

    void addNewComplaint(Long userIdEntry, Optional<MultipartFile> file, String desc, ComplaintCategories cat, ComplaintUrgencies urgency);

    //For admin and agent only

    void answerComplaint(Integer complaintId, String complaintResponse, ComplaintStatus status);

    void cancelComplaint(Integer complaintId);

    void attachFile(Integer complaintId, MultipartFile file);


    List<Complaint> searchByCategory(ComplaintCategories cat);

    List<Complaint> searchByDate(Date q);

    List<Complaint> selectAll();

    //user constraint : can select only cmplaints with cin matches user.getcin executing
    Complaint selectById(Integer id);


    String uploadFile(Integer complaintId , MultipartFile file);



    void saveComplaint(Complaint complaint);

    Complaint getComplaintById(Integer complaintId);


    void updateComplaintStatusIfAllTasksCompleted(Complaint complaint);
}
