package com.example.investisment.Repository;

import com.example.investisment.entity.immobilier;
import com.example.investisment.entity.investisment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface investisment_repo extends JpaRepository<investisment,Long> {
    List<investisment> findByImb(immobilier imb);

}
