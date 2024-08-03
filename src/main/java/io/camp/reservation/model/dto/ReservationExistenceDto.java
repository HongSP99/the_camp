package io.camp.reservation.model.dto;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationExistenceDto {
    private Long zoneSeq;
    private LocalDate reservationStartDate;
    private LocalDate reservationEndDate;
    private boolean existence;
}