package tn.fintech.fintech.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.fintech.fintech.domain.Credit;


public interface CreditRepository extends JpaRepository<Credit, Long> {
}
