package tn.esprit.mfb.Repository;

import tn.esprit.mfb.entity.Demand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface demande_repo extends JpaRepository<Demand,Long> {

}
