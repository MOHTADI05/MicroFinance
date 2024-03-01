package tn.fintech.mfb.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.fintech.mfb.entity.BankAccount;
@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
}
