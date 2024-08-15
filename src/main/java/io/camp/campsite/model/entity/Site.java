package io.camp.campsite.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.camp.campsite.model.dto.SiteDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Getter;

@Entity
@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor
public class Site {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @Column
    private String title;

    @ManyToOne(cascade = CascadeType.ALL)
    private Zone zone;

    public Site(String title , Zone zone){
        this.title = title;
        this.zone  = zone;
    }


    public SiteDto toDto(){
        return SiteDto.builder().title(title).build();
    }
}
