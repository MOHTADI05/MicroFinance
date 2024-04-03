package com.example.investisment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;


@Entity
@Setter
@Getter
@AllArgsConstructor
public class user implements Serializable{

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private String cin ;
    @Enumerated(EnumType.STRING)
    private TypeUser role;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "u_fav_id")
    private favorite uFav;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "u_rat_id")
    private rating uRat;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Account ACC;


    public user() {
    }

    public void setUSERCode(String ratingtCode) {

    }

    @Override
    public String toString() {
        return "user{" +
                "cin=" + cin +
                ", uFav=" + uFav +
                ", uRat=" + uRat +
                ", ACC=" + ACC +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        user user = (user) o;
        return Objects.equals(cin, user.cin) && Objects.equals(uFav, user.uFav) && Objects.equals(uRat, user.uRat) && Objects.equals(ACC, user.ACC);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cin, uFav, uRat, ACC);
    }
}




