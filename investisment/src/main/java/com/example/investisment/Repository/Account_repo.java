package com.example.investisment.Repository;

import com.example.investisment.entity.Account;
import com.example.investisment.entity.user;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface Account_repo extends JpaRepository<Account,Long> {
    Optional<Account> findByUser(user user);

}
