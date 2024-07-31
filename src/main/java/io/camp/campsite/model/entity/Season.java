package io.camp.campsite.model.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Season {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @Column
    private LocalDateTime start;

    @Column
    private LocalDateTime end;

    @Column
    private SeasonType season;

    @ManyToOne(cascade = CascadeType.ALL)
    private Campsite campsite;
}
