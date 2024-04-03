package tn.esprit.spring.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "client")
@Getter
@Setter
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idClient")
    private Long idClient;

    @Column(name = "name")
    private String ClientName;

    @Column(name = "ClientEmail")
    private String ClientEmail;
    @Column(name = "NetIncome")
    private double NetIncome ;
    private Boolean Credit_authorization;

   @OneToOne
   private Product product;

    @ManyToOne
    @JoinColumn(name = "due_date_id")
    private DueDate dueDate;

    // other fields...

    // getters and setters...
    public void setNetIncome(double netIncome) {
        this.NetIncome = netIncome;
    }

    public Long getidClient() {
        return idClient;
    }

    public void setidClient(Long idClient) {
        this.idClient = idClient;
    }

    public String getClientName() {
        return ClientName;
    }

    public void setClientName(String ClientName) {
        this.ClientName = ClientName;
    }
public Boolean getCredit_authorization() {
        return Credit_authorization;
    }
    public void setCredit_authorization(Boolean credit_authorization) {
        Credit_authorization = credit_authorization;
    }
    public String getClientEmail() {
        return ClientEmail;
    }

    public void setClientEmail(String ClientEmail) {
        this.ClientEmail = ClientEmail;
    }

    // other getters and setters...
}
