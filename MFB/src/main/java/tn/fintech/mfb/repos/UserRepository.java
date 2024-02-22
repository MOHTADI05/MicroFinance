package tn.fintech.mfb.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.fintech.mfb.domain.User;


public interface UserRepository extends JpaRepository<User, Long> {
}
