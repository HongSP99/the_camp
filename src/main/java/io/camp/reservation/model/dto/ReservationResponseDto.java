package io.camp.reservation.model.dto;

import io.camp.campsite.model.entity.Campsite;
import io.camp.campsite.model.entity.Site;
import io.camp.campsite.model.entity.Zone;
import io.camp.reservation.model.ReservationState;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationResponseDto {
    private Long reservationId;

    private LocalDateTime reserveStartDate;
    private LocalDateTime reserveEndDate;
    private int adults;
    private int children;
    private int totalPrice;
    private LocalDateTime createdAt;

    private ReservationState reservationState;

    @Setter(AccessLevel.NONE)
    private String campsiteName;
    private String zoneName;
    private int siteNumber;

    public void setCampsiteName(Campsite campsite){
        this.campsiteName = campsite.getFacltNm();
    }

    public void setZoneName(Zone zone){
        this.zoneName = zone.getTitle();
    }

    public void setSiteNumber(Site site){
        this.siteNumber = site.getNumber();
    }
}
