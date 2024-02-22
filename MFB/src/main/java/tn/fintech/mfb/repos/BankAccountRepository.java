package tn.fintech.mfb.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.fintech.mfb.domain.BankAccount;


public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
}
