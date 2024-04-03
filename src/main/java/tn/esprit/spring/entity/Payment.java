package tn.esprit.spring.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;


@Entity
@Getter
public class Payment implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int paymentNumber;
    @Getter
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private Date paymentDate;
    @Getter
    @NotNull(message = "Le solde ne peut pas être nul")
    private double balance;
    @Getter
    @NotNull(message = "Le montant principal payé ne peut pas être nul")
    @Min(value = 0, message = "Le montant principal payé ne peut pas être négatif")
    private double principalPaid;
    @NotNull(message = "Le montant des intérêts payés ne peut pas être nul")
    @Min(value = 0, message = "Le montant des intérêts payés ne peut pas être négatif")
    private Double interestPaid;
    @NotNull(message = "Le montant total des intérêts accumulés ne peut pas être nul")
    @Min(value = 0, message = "Le montant total des intérêts accumulés ne peut pas être négatif")
    private Double accumulatedInterest;


    public Payment(int paymentNumber, Date paymentDate, double balance, double principalPaid, double interestPaid, double accumulatedInterest)
    {
        setPaymentNumber(paymentNumber);
        setPaymentDate(paymentDate);
        setBalance(balance);
        setPrincipalPaid(principalPaid);
        setInterestPaid(interestPaid);
        setAccumulatedInterest(accumulatedInterest);
    }

    public Payment() {

    }

    public void setPaymentNumber(int paymentNumber) { this.paymentNumber = paymentNumber; }

    public void setPaymentDate(Date paymentDate)
    {
        this.paymentDate = paymentDate;
    }

    public void setBalance(double balance)
    {
        this.balance = balance;
    }

    public void setPrincipalPaid(double principalPaid)
    {
        this.principalPaid = principalPaid;
    }

    public double getInterestPaid()
    {
        return this.interestPaid;
    }
    public void setInterestPaid(double interestPaid)
    {
        this.interestPaid = interestPaid;
    }

    public double getAccumulatedInterest()
    {
        return this.accumulatedInterest;
    }
    public void setAccumulatedInterest(double accumulatedInterest) { this.accumulatedInterest = accumulatedInterest; }

    @Override
    public String toString()
    {
        return "[" + paymentNumber + "," + paymentDate + "," + balance + "," + principalPaid + "," + interestPaid + "," + accumulatedInterest + "]";
    }


}