package tn.fintech.fintech.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.fintech.fintech.domain.User;


public interface UserRepository extends JpaRepository<User, Long> {

}
