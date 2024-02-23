package tn.esprit.mfb.Repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.mfb.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository <User,Long> {

    Optional<User> findByEmail(String email);

}
