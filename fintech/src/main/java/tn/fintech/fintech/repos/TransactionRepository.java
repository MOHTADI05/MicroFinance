package tn.fintech.fintech.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.fintech.fintech.domain.BankAccount;
import tn.fintech.fintech.domain.Transaction;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {


    Transaction findFirstBySourceRIB(BankAccount bankAccount);

    List<Transaction> findBySourceRIBRib(Long bankAccountId);
}
