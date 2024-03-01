package tn.fintech.mfb.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.fintech.mfb.entity.Credit;


public interface CreditRepository extends JpaRepository<Credit, Long> {
}
