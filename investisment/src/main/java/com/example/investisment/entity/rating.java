package com.example.investisment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Entity
@Setter
@Getter
@AllArgsConstructor
public class rating {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)


        private Long comment_id;
    @Column(insertable=false, updatable=false)
    private String cin ;
        private Long imb_id;
        private String comment_text;
        private Long rating;
    @OneToMany(mappedBy = "uRat")
    private Set<user> rat;

    @OneToMany(mappedBy = "imbR")
    private Set<immobilier> ratI;

    public rating() {
    }

     @Override
    public String toString() {
        return "rating{" +
                "comment_id=" + comment_id +
                ", cin='" + cin + '\'' +
                ", imb_id=" + imb_id +
                ", comment_text='" + comment_text + '\'' +
                ", rating=" + rating +
                '}';
    }

    public void setratingtCode(UUID uuid, String string) {
    }
}
