package com.example.investisment.Repository;

import com.example.investisment.entity.inv_history;
import org.springframework.data.jpa.repository.JpaRepository;

public interface inv_history_repo extends JpaRepository<inv_history,Long> {
}
