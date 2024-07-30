package io.camp.like.model;


import jakarta.persistence.*;

@Entity
@Table(name = "like")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column( name = "like_id")
    private Long id;

    /*@ManyToOne
    @JoinColumn*/
}
