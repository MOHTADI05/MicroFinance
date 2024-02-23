package tn.fintech.mfb.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import tn.fintech.mfb.model.TypeUser;


@Entity
@Getter
@Setter
public class User {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cin;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    @Column(nullable = false)
    private Integer age;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String adresse;

    @Column
    private String gender;

    @Column(nullable = false, unique = true)
    private Integer phoneNum;

    @Column(name = "\"role\"")
    @Enumerated(EnumType.STRING)
    private TypeUser role;

    @OneToMany(mappedBy = "user")
    private Set<Credit> credits;

    @OneToMany(mappedBy = "user")
    private Set<BankAccount> bankAccounts;

}
