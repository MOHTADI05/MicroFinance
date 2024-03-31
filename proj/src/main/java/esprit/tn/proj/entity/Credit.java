package esprit.tn.proj.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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


    private Integer minamount;
    private Integer maxamount;
    private String name;
    private String description;


@ManyToOne
@JoinColumn(name = "packc_idp")
@JsonIgnore // Ignorer cette propriété lors de la sérialisation JSON
    PackC packC;


}
