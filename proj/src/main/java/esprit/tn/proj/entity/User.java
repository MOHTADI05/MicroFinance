package esprit.tn.proj.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long cin;
    private String name;
    private String secondName;
    @Temporal (TemporalType.DATE)
    private Date birthDay ;
    private Long phoneNum;
    private String email;
    private String image;
    private String adresse;
    private String password;
    //null par defaut / true autorisé/false non autorisé
    private Boolean Credit_authorization;


   @OneToMany(cascade = CascadeType.ALL, mappedBy="client")
   private Set<DemandeCredit> credits;






}
