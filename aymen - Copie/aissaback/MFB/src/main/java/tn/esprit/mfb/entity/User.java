package tn.esprit.mfb.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;


@Entity
public class User implements Serializable, UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonManagedReference
    private Long cin;

    @Size(min = 2, max = 30)
    @JsonManagedReference
    private String nom;

    @JsonManagedReference
    private String prenom;
    @JsonManagedReference
    private Integer age;

    @Email
    @Column(unique = true)
    private String email;

    private String password;

    private String adresse;

    private String gender;

    @Min(value = 10000000)
    @Max(value= 99999999)
    private Integer phoneNum;

    @Enumerated(EnumType.STRING)
    private TypeUser role;

    public void setRole(TypeUser role) {
        this.role = role;
    }

    private boolean isbloked;

    private Integer code;

    private String performance;
    private String potentiel;

    @Enumerated(EnumType.STRING)
    private TalentReview classification;


    public void setCredit_authorization(Boolean credit_authorization) {
        Credit_authorization = credit_authorization;
    }

    public void setCredits(Set<DemandeCredit> credits) {
        this.credits = credits;
    }

    public Boolean getCredit_authorization() {
        return Credit_authorization;
    }

    public Set<DemandeCredit> getCredits() {
        return credits;
    }

    private Boolean Credit_authorization;


    @OneToMany(cascade = CascadeType.ALL, mappedBy="client",fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<DemandeCredit> credits;

    public Account getACC() {
        return ACC;
    }

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Account ACC;

    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "userc", fetch = FetchType.EAGER)
    @JsonBackReference // Add this annotation
    private List<Complaint> complaints;


    public List<Complaint> getComplaints() {
        return complaints;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.MERGE)
    @JsonManagedReference
    private List<Notification> notifications;
    public void setClassification(TalentReview classification) {
        this.classification = classification;
    }

    public TalentReview getClassification() {
        return classification;
    }

    public void setPerformance(String performance) {
        this.performance = performance;
    }

    public void setPotentiel(String potentiel) {
        this.potentiel = potentiel;
    }

    public String getPerformance() {
        return performance;
    }

    public String getPotentiel() {
        return potentiel;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer token) {
        this.code = token;
    }

    public void setIsbloked(boolean isbloked) { this.isbloked = isbloked; }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }


    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Long getCin() {
        return cin;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public Integer getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }
    public String getAdresse() {
        return adresse;
    }

    public String getGender() {
        return gender;
    }

    public Integer getPhoneNum() {
        return phoneNum;
    }

    public TypeUser getRole() {
        return role;
    }

    public boolean isIsbloked()  { return isbloked; }


    public void setPassword(String password) {
        this.password = password;
    }

    public void setComplaints(List<Complaint> complaints) {
        this.complaints = complaints;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }
}
