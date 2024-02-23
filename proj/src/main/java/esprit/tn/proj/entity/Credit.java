package esprit.tn.proj.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
@Entity
@Setter
@Getter
public class Credit implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idcredit;

    private Integer amount;

    private LocalDate demandedate;

    private LocalDate obtainingdate;

    private String state;

    private Integer mounthlypayment;

    private Integer interest;


}
