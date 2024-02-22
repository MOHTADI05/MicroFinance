package tn.fintech.mfb.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import tn.fintech.mfb.model.TypeAccount;


@Entity
@Getter
@Setter
public class BankAccount {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rib;

    @Column
    private Double balancer;

    @Column
    private LocalDate openDate;

    @Column
    private Integer status;

    @Column
    private String transactionHistory;

    @Column
    private String loyaltyScore;

    @Column
    @Enumerated(EnumType.STRING)
    private TypeAccount typeAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "sourceRIB")
    private Set<Transaction> transactions;

}
