package tn.fintech.fintech.model;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BankAccountDTO {

    private Long rib;
    private Double balance;
    private LocalDate openDate;
    private Integer Code;
    private Integer loyaltyScore;
    private TypeAccount typeAccount;
    private Long user;

}
