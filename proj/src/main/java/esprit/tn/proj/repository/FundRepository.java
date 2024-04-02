package esprit.tn.proj.repository;

import esprit.tn.proj.entity.Fund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FundRepository extends JpaRepository<Fund, Long> {
}
