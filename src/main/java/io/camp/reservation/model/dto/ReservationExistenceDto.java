package io.camp.reservation.model.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReservationExistenceDto {
    private Long siteSeq;
    private LocalDate reservationStartDate;
    private LocalDate reservationEndDate;
    private boolean existence;
}