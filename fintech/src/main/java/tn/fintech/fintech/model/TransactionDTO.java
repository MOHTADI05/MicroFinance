package tn.fintech.fintech.model;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TransactionDTO {

    private Long transactionId;
    private LocalDate transactionDate;
    private Double amount;
    private Long destination;
    private Long sourceRIB;

}
