package io.camp.reservation.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.camp.reservation.model.QReservation;
import io.camp.reservation.model.Reservation;
import io.camp.reservation.model.ReservationState;
import io.camp.reservation.model.dto.ReservationExistenceDto;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class ReservationRepositoryCustomImpl implements ReservationRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public boolean checkReservationExistence(ReservationExistenceDto dto) {
        QReservation reservation = QReservation.reservation;
        List<ReservationState> stateList = Arrays.asList(
                ReservationState.RESERVATION_DONE,
                ReservationState.NO_CANCEL
        );

        log.info("DTO: " + dto.toString());  // DTO 값 확인을 위한 로깅
        log.info("DTO.STARTDATE : " + dto.getReservationStartDate());
        log.info("DTO.ENDDATE : " + dto.getReservationEndDate());

        Reservation existingReservation = jpaQueryFactory.selectFrom(reservation)
                .where(
                        reservation.site.seq.eq(dto.getSiteSeq())
                                .and(reservation.reservationState.in(stateList))
                                .and(
                                        reservation.reserveStartDate.lt(dto.getReservationEndDate())
                                                .and(reservation.reserveEndDate.gt(dto.getReservationStartDate()))
                                )
                )
                .fetchFirst();

        log.info("Existing Reservation: " + (existingReservation != null ? existingReservation.toString() : "None")); // 결과 로깅

        return existingReservation != null;
    }
}
