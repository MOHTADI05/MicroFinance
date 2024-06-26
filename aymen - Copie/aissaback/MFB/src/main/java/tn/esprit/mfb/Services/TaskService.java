package tn.esprit.mfb.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.mfb.Repository.ComplaintRepository;
import tn.esprit.mfb.Repository.TaskRepository;
import tn.esprit.mfb.Repository.UserRepository;
import tn.esprit.mfb.entity.*;
import tn.esprit.mfb.serviceInterface.ITaskService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService implements ITaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ComplaintRepository complaintRepository;
    private final NotificationService notificationService;
    private final ComplaintService complaintService;

    @Autowired
    public TaskService(TaskRepository taskRepository,
                       UserRepository userRepository,
                       ComplaintRepository complaintRepository,
                       NotificationService notificationService,
                       ComplaintService complaintSrvice

                       ) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.complaintRepository = complaintRepository;
        this.notificationService = notificationService;
        this.complaintService = complaintSrvice;
    }


    @Override
    public void createTask(String name, Optional<String> description, int effort, Date dueDate, Long assignedUserId, Integer complaintId) {
        User assignedUser = userRepository.findById(assignedUserId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid assigned user ID."));

        Complaint complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid complaint ID."));

        Date createdAt = new Date();

        Task task = new Task();
        task.setName(name);
        description.ifPresent(task::setDescription);
        if (effort != 100) {
            task.setEffort(effort);
        }
        task.setDueDate(dueDate);
        task.setAssignedUser(assignedUser);
        task.setComplaint(complaint);
        task.setAssignedBy("ADMIN");
        task.setCreatedAt(createdAt);

        complaint.setComplaintStatus(ComplaintStatus.DATAVERIFICATION);

        task.setStatus(TaskStatus.PENDING);

        // Calculate total effort of all tasks associated with the complaint
        double totalEffort = taskRepository.sumEffortByComplaintId(complaintId);

        if (totalEffort + effort > 100) {
            throw new IllegalArgumentException("Total effort of all tasks cannot exceed 100.");
        }
        taskRepository.save(task);
//        complaintRepository.save(complaint);
//        if (complaintService.upComplaintProgress(task.getComplaint().getComplaintId())) {
//            taskRepository.save(task);
//        }
        notificationService.createNotificationp(assignedUser, Optional.of(complaint), "Task Created", "Task \"" + task.getName() + "\" <waiting>");
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    @Override
    public Task updateTask(Long id, Task updatedTask) {
        return taskRepository.findById(id)
                .map(task -> {
                    task.setName(updatedTask.getName());
                    task.setDescription(updatedTask.getDescription());
                    task.setDueDate(updatedTask.getDueDate());
                    task.setStatus(updatedTask.getStatus());
                    return taskRepository.save(task);
                })
                .orElseThrow(() -> new IllegalArgumentException("Task with id " + id + " not found"));
    }

    @Override
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public void addTprogress(Long taskId, double tProgressToAdd) {
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            double currentTProgress = task.getTProgress();
            double newTProgress = currentTProgress + tProgressToAdd;

            // TProgress
            double updatedProgress = Math.min(Math.max(newTProgress, 0.0), 100.0);

            if (updatedProgress <= 100.0) {
                task.setTProgress(updatedProgress);
                taskRepository.save(task);
                notificationService.createNotificationp(task.getAssignedUser(), Optional.of(task.getComplaint()), "task progressed", String.format("%.2f %% progress added to task", updatedProgress));
                double cprogress= task.getComplaint().getCProgress() + updatedProgress * task.getEffort()/100;
                complaintService.upComplaintProgress(task.getComplaint().getComplaintId());
            } else {
                throw new IllegalArgumentException("Progress cannot exceed 100%.");
            }
        } else {
            throw new IllegalArgumentException("Task with ID " + taskId + " not found.");
        }
    }


    @Override
    public Task markTaskAsCompleted(Long id) {
        return taskRepository.findById(id)
                .map(task -> {
                    task.setStatus(TaskStatus.COMPLETED);
                    User assignedUser = task.getAssignedUser(); // Get the assigned user
                    Complaint cl = task.getComplaint(); // Get the related complaint

                    if (assignedUser != null && cl != null) {
                        notificationService.createNotificationp(assignedUser, Optional.of(cl), "Task Completed", "Task \"" + task.getName() + "\" has been completed.");
                    } else {
                        // Handle the case if assignedUser or cl is null
                        throw new IllegalStateException("Assigned user or complaint is null for task with id " + id);
                    }

                    return taskRepository.save(task);
                })
                .orElseThrow(() -> new IllegalArgumentException("Task with id " + id + " not found"));
    }

    @Override
    public Task acceptTaskAs(Long id) {
        return taskRepository.findById(id)
                .map(task -> {
                    task.setStatus(TaskStatus.INPROGRESS);
                    task.setStartDate(new Date());
                    User assignedUser = task.getAssignedUser(); // Get the assigned user
                    Complaint cl = task.getComplaint(); // Get the related complaint

                    // Check if assignedUser and cl are not null before creating the notification
                    if (assignedUser != null && cl != null) {
                        notificationService.createNotificationp(assignedUser, Optional.of(cl), "Task Accepted", "Task \"" + task.getName() + "\" has been accepted and is in progress.");
                    } else {
                        // Handle the case if assignedUser or cl is null
                        throw new IllegalStateException("Assigned user or complaint is null for task with id " + id);
                    }

                    return taskRepository.save(task);
                })
                .orElseThrow(() -> new IllegalArgumentException("Task with id " + id + " not found"));
    }
    @Override
    public Task endTaskAs(Long id, TaskStatus status) {
        return taskRepository.findById(id)
                .map(task -> {
                    task.setStatus(status);
                    task.setEndDate(new Date());
                    User assignedUser = task.getAssignedUser();
                    Complaint complaint = task.getComplaint();

                    if (assignedUser != null && complaint != null) {
                        notificationService.createNotificationp(assignedUser, Optional.of(complaint), "Task Ended", "Task \"" + task.getName() + "\" has been ended.");
                    } else {
                        throw new IllegalStateException("Assigned user or complaint is null for task with id " + id);
                    }
                    List<Task> tasks = taskRepository.findByComplaint(complaint);
                    boolean allOtherTasksCompleted = tasks.stream()
                            .filter(t -> !t.getId().equals(id)) // Exclude the current task
                            .allMatch(t -> t.getStatus() == TaskStatus.COMPLETED);

                    if (allOtherTasksCompleted) {
                        complaint.setComplaintStatus(ComplaintStatus.VERIFIED);
                        complaintRepository.save(complaint);
                        notificationService.createNotificationp(assignedUser, Optional.of(complaint), "Complaint verified", "Complaint \"" + complaint.getComplaintId() + "\" has been accepted and is in progress.");

                    }



                    // Save task
                    task.setTProgress(100);
                    complaintService.upComplaintProgress(task.getComplaint().getComplaintId());


                    return taskRepository.save(task);

                })
                .orElseThrow(() -> new IllegalArgumentException("Task with id " + id + " not found"));
    }



}
