package tn.fintech.mfb.model;

import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BankAccountDTO {

    private Long rib;

    private Double balancer;

    private LocalDate openDate;

    private Integer status;

    @Size(max = 255)
    private String transactionHistory;

    @Size(max = 255)
    private String loyaltyScore;

    private TypeAccount typeAccount;

    private Long user;

}
