package com.example.investisment.Repository;

import com.example.investisment.entity.TypeUser;
import com.example.investisment.entity.user;
import org.springframework.data.jpa.repository.JpaRepository;

public interface user_repo extends JpaRepository<user,Long> {
    user findByRole(TypeUser role);
}
