package io.camp.campsite.model.dto;

import io.camp.campsite.model.entity.Site;
import io.camp.campsite.model.entity.Zone;
import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZoneDto {

    private Long seq;

    private String title;

    private String intro;

    private LocalTime checkin;

    private LocalTime checkout;

    private int offSeasonPrice;

    private int peakSeasonPrice;

    private int bestPeakSeasonPrice;

    private int numOfSite;

    private long campSite;

    private String campSiteImg;

    private String campSiteName;

    private List<SiteDto> sites;




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

    public static ZoneDto fromEntity(Zone zone){
        ZoneDto dto = new ZoneDto();

        dto.setTitle(zone.getTitle());
        dto.setIntro(zone.getIntro());
        dto.setCheckin(zone.getCheckin());
        dto.setCheckout(zone.getCheckout());
        dto.setOffSeasonPrice(zone.getOffSeasonPrice());
        dto.setPeakSeasonPrice(zone.getPeakSeasonPrice());
        dto.setBestPeakSeasonPrice(zone.getBestPeakSeasonPrice());
        dto.setCampSiteImg(zone.getCampsite().getFirstImageUrl());
        dto.setCampSiteName(zone.getCampsite().getFacltNm());

        return dto;
    }
}
