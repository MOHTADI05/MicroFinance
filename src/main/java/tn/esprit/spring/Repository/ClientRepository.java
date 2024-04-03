package tn.esprit.spring.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.spring.entity.Client;

import java.util.Optional;

@Repository
public interface ClientRepository extends CrudRepository<Client,Long> {

    /*@Query("SELECT c FROM Client c WHERE c.agent=:id")
    Set<Client> getClientsByAgent(@Param("id") Long idAgent);*/
    @Query(" select u from Client u " + " where u.ClientEmail = ?1")
    Optional<Client> findUserWithName(String username);

    @Query(" select u from Client u " + " where u.ClientEmail = ?1")
    Client findUser(String username);

}
