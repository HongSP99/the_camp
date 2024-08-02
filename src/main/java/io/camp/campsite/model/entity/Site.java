package io.camp.campsite.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.camp.campsite.model.dto.SiteDto;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
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


    public SiteDto toDto(){
        return SiteDto.builder().number(number).build();
    }
}
