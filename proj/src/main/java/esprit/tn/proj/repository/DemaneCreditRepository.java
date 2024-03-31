package esprit.tn.proj.repository;

import esprit.tn.proj.entity.Credit;
import esprit.tn.proj.entity.DemandeCredit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface DemaneCreditRepository extends JpaRepository<DemandeCredit, Long> {

    List<DemandeCredit> findByClientIdAndCompletedIsFalseAndStateIsFalse(Long clientId);
    List<DemandeCredit> findByClientIdAndStateIsFalse(Long id);
    DemandeCredit findByClientIdAndCompletedIsTrueAndStateIsTrue(Long id);
    DemandeCredit  findTopByClientIdAndCompletedIsTrueAndStateIsTrueOrderByObtainingdateDesc(Long clientId);


}
