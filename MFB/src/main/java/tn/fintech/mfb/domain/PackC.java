package tn.fintech.mfb.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import tn.fintech.mfb.model.PackType;


@Entity
@Getter
@Setter
public class PackC {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idP;

    @Column
    @Enumerated(EnumType.STRING)
    private PackType typePack;

    @Column(name = "\"description\"", columnDefinition = "longtext")
    private String description;

    @Column
    private String name;

    @OneToMany(mappedBy = "idP")
    private Set<Credit> idcredit;

}
