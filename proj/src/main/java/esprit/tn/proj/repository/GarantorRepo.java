package esprit.tn.proj.repository;

import esprit.tn.proj.entity.Garantor;
import esprit.tn.proj.entity.PackC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GarantorRepo extends JpaRepository<Garantor, Long> {
}
