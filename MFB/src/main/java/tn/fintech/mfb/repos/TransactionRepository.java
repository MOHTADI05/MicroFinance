package tn.fintech.mfb.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.fintech.mfb.domain.BankAccount;
import tn.fintech.mfb.domain.Transaction;


public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Transaction findFirstBySourceRIB(BankAccount bankAccount);

}
