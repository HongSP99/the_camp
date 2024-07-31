package io.camp.campsite.model.dto;

import io.camp.campsite.model.entity.Zone;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.LocalTime;


@Data
@ToString
public class ZoneDto {

    private String title;

    private String intro;

    private LocalTime checkin;

    private LocalTime checkout;

    private int offSeasonPrice;

    private int peakSeasonPrice;

    private int bestPeakSeasonPrice;

    private int numOfSite;

    private long campSite;


    public Zone toEntity(){
       return Zone.builder()
                .title(title)
                .intro(intro)
                .checkin(checkin)
                .checkout(checkout)
                .offSeasonPrice(offSeasonPrice)
                .peakSeasonPrice(peakSeasonPrice)
                .bestPeakSeasonPrice(bestPeakSeasonPrice)
                .build();
    }
}
