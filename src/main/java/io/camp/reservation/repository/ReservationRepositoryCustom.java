package io.camp.reservation.repository;

import io.camp.campsite.model.entity.Zone;
import io.camp.reservation.model.QReservation;
import io.camp.reservation.model.Reservation;
import io.camp.reservation.model.dto.ReservationExistenceDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReservationRepositoryCustom {
    boolean checkReservationExistence(ReservationExistenceDto dto);

    Page<Reservation> findAllReservationWithPaging(Pageable pageable);
}
