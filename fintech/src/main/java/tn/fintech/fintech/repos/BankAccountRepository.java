package tn.fintech.fintech.repos;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;
import tn.fintech.fintech.domain.BankAccount;

import java.util.Optional;
import java.util.function.Function;

@Repository

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {


    Optional<BankAccount> findFirstByCode(int code);
}
