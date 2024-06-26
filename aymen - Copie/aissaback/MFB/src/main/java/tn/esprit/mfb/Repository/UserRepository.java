package tn.esprit.mfb.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.mfb.entity.ComplaintCategories;
import tn.esprit.mfb.entity.TalentReview;
import tn.esprit.mfb.entity.TypeUser;
import tn.esprit.mfb.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository <User,Long>, JpaRepository <User,Long> {

    Optional<User> findByEmail(String email);

    User findByCin(Long cin);

    // User findByEmail(String email);
     User findByRole(TypeUser role);




    List<User> findByClassification(TalentReview talent);


}
