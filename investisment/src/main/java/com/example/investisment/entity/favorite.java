package com.example.investisment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;
@Entity

@Setter
@Getter
@AllArgsConstructor
public class favorite implements Serializable{



        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long favorite_id;
    @Column(insertable=false, updatable=false)
    private String cin ;
        private Long imb_id;

    @OneToMany(mappedBy = "uFav")
    private Set<user> fav;

    @OneToMany(mappedBy = "imbF")
    private Set<immobilier> favI;

    public favorite() {

    }


    public void setinvestismentCode(UUID uuid, String string) {
    }


    }

