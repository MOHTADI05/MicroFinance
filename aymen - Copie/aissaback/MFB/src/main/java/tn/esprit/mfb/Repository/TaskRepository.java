package tn.esprit.mfb.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tn.esprit.mfb.entity.Complaint;
import tn.esprit.mfb.entity.Task;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByComplaint(Complaint complaint);

    List<Task> findByComplaint_ComplaintId(Integer complaintId);

    @Query("SELECT COALESCE(SUM(t.effort), 0) FROM Task t WHERE t.complaint.complaintId = :complaintId")
    int sumEffortByComplaintId(Integer complaintId);
}
