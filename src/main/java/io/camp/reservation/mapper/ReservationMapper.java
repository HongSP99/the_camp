package io.camp.reservation.mapper;

import io.camp.reservation.model.Reservation;
import io.camp.reservation.model.ReservationState;
import io.camp.reservation.model.dto.ReservationPostDto;
import io.camp.reservation.model.dto.ReservationResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReservationMapper {

    @Mapping(target = "reservationState", ignore = true)
    @Mapping(target = "id", ignore = true)
    Reservation reservationPostDtoToReservation(ReservationPostDto reservationPostDto);

    default ReservationResponseDto reservationToReservationResponseDto(Reservation reservation){
        ReservationResponseDto responseDto = new ReservationResponseDto();

        responseDto.setReservationId(reservation.getId());
        responseDto.setReservationState(reservation.getReservationState());
        responseDto.setAdults(reservation.getAdults());
        responseDto.setChildren(reservation.getChildren());
        responseDto.setCampsiteName(reservation.getCampsite());
        responseDto.setCreatedAt(reservation.getCreatedAt());

        return responseDto;
    }
}