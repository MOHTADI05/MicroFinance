package esprit.tn.proj.repository;

import esprit.tn.proj.entity.HistoryC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
@Repository
public interface HistoryCRepository extends JpaRepository<HistoryC, Long> {

    List<HistoryC> findByDemandecreditsIdDemandecredit(Long id);

    @Query("SELECT MAX(h.SupposedDate) FROM HistoryC h WHERE h.demandecredits.idDemandecredit = :idcredit")
    LocalDate findMaxSupposedDateByDemandecreditsIdDemandecredit(@Param("idcredit") Long idcredit);


}
