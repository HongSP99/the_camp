package io.camp.reservation.mapper;

import io.camp.reservation.model.Reservation;
import io.camp.reservation.model.dto.ReservationPostDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-02T13:15:44+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.12 (Ubuntu)"
)
@Component
public class ReservationMapperImpl implements ReservationMapper {

    @Override
    public Reservation reservationPostDtoToReservation(ReservationPostDto reservationPostDto) {
        if ( reservationPostDto == null ) {
            return null;
        }

        Reservation reservation = new Reservation();

        reservation.setUser( reservationPostDto.getUser() );

        return reservation;
    }
}
