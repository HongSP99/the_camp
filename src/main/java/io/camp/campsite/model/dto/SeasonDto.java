package io.camp.campsite.model.dto;

import io.camp.campsite.model.entity.Campsite;
import io.camp.campsite.model.entity.Season;
import io.camp.campsite.model.entity.SeasonType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Builder
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SeasonDto {

    private Long seq;

    private LocalDate start;

    private LocalDate end;

    private SeasonType type;

    private long campsite;

    public Season toEntity(Campsite campsite){
        return Season.builder()
                .start(start)
                .end(end)
                .type(type)
                .campsite(campsite)
                .build();
    }
}
