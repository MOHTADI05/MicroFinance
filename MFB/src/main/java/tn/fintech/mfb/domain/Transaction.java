package tn.fintech.mfb.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Transaction {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @Column
    private LocalDate transactionDate;

    @Column
    private Double amount;

    @Column
    private Integer recipient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sourceribid")
    private BankAccount sourceRIB;

}
