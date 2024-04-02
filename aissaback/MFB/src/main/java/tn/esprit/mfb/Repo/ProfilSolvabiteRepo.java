package tn.esprit.mfb.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.mfb.entity.ProfilSolvabilite;
import tn.esprit.mfb.entity.User;

@Repository
public interface ProfilSolvabiteRepo  extends CrudRepository<ProfilSolvabilite,Long>, JpaRepository<ProfilSolvabilite,Long> {

}
