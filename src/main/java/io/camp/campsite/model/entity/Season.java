package io.camp.campsite.model.entity;

import io.camp.campsite.model.dto.SeasonDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Season {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @Column
    private LocalDate start;

    @Column
    private LocalDate end;

    @Column
    private SeasonType type;

    @ManyToOne
    private Campsite campsite;


    public SeasonDto toDto(){
        return SeasonDto.builder()
                .seq(seq)
                .start(start)
                .end(end)
                .type(type)
                .campsite(campsite.getSeq()).build();
    }



}
