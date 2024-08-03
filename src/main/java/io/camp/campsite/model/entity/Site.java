package io.camp.campsite.model.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Site {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @Column
    private int number;

    @ManyToOne(cascade = CascadeType.ALL)
    private Zone zone;



    public Site(int number , Zone zone){
        this.number = number;
        this.zone  = zone;
    }
}
