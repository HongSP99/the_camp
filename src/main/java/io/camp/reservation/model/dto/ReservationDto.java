package io.camp.reservation.model.dto;

import io.camp.reservation.model.Reservation;
import io.camp.reservation.model.ReservationState;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class ReservationDto {
    private Long ReservationId;
    private LocalDateTime reservationStartDate;
    private LocalDateTime reservationEndDate;
    private int adults;
    private int children;
    private int totalPrice;
    private ReservationState reservationState;
    private Long userSeq;
    private Long campsiteSeq;

    public static ReservationDto fromEntity(Reservation reservation){
        ReservationDto dto = new ReservationDto();
        dto.setReservationId(reservation.getId());
        dto.setReservationStartDate(reservation.getReserveStartDate());
        dto.setReservationEndDate(reservation.getReserveEndDate());
        dto.setAdults(reservation.getAdults());
        dto.setChildren(reservation.getChildren());
        dto.setTotalPrice(reservation.getTotalPrice());
        dto.setReservationState(reservation.getReservationState());
        if(reservation.getUser() != null){
            dto.setUserSeq(reservation.getUser().getSeq());
        }
        if(reservation.getCampsite() != null){
            dto.setCampsiteSeq(reservation.getCampsite().getSeq());
        }

        return dto;
    }
}
