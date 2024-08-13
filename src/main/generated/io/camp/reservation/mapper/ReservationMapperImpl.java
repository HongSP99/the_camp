package io.camp.reservation.mapper;

import io.camp.reservation.model.Reservation;
import io.camp.reservation.model.dto.ReservationPostDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-13T15:13:56+0900",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.8.jar, environment: Java 17.0.10 (Oracle Corporation)"
)
@Component
public class ReservationMapperImpl implements ReservationMapper {

    @Override
    public Reservation reservationPostDtoToReservation(ReservationPostDto reservationPostDto) {
        if ( reservationPostDto == null ) {
            return null;
        }

        Reservation reservation = new Reservation();

        reservation.setReserveStartDate( reservationPostDto.getReserveStartDate() );
        reservation.setReserveEndDate( reservationPostDto.getReserveEndDate() );
        reservation.setAdults( reservationPostDto.getAdults() );
        reservation.setChildren( reservationPostDto.getChildren() );
        reservation.setTotalPrice( reservationPostDto.getTotalPrice() );

        return reservation;
    }
}
