package tn.esprit.mfb.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Amortissement implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idA;
    private float montantR;
    private float interest;
    private float amortissement;
    private float mensualite;

    @ManyToOne
    @JsonIgnore // Ignorer cette propriété lors de la sérialisation JSON
    private DemandeCredit demandeCredit;


    public Amortissement(float montantR, float interest, float i, float mensualite) {
        super();
        this.montantR = montantR;
        this.interest = interest;
        this.amortissement = i;
        this.mensualite = mensualite;
    }
}
