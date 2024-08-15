package io.camp.user.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.camp.payment.model.QPayment;
import io.camp.reservation.model.QReservation;
import io.camp.reservation.model.ReservationState;
import io.camp.review.model.dto.ReviewDto;
import io.camp.user.model.QUser;
import io.camp.user.model.User;
import io.camp.user.model.dto.UserReservationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static io.camp.payment.model.QPayment.payment;
import static io.camp.reservation.model.QReservation.reservation;
import static io.camp.user.model.QUser.user;

@RequiredArgsConstructor
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<UserReservationDto> userGetReservations(User loginUser, Pageable pageable) {
        QueryResults<UserReservationDto> results = jpaQueryFactory
                .select(Projections.constructor(UserReservationDto.class,
                        payment.campsiteName.as("campsiteName"),
                        payment.paymentId.as("paymentId"),
                        reservation.id.as("reservationId"),
                        reservation.totalPrice.as("totalPrice"),
                        reservation.adults.as("adults"),
                        reservation.children.as("children"),
                        reservation.createdAt.as("createdAt"),
                        reservation.reserveStartDate.as("reserveStartDate"),
                        reservation.reserveEndDate.as("reserveEndDate")
                ))
                .from(user)
                .join(payment)
                .on(user.seq.eq(payment.user.seq))
                .join(reservation)
                .on(payment.reservation.id.eq(reservation.id))
                .where(user.seq.eq(loginUser.getSeq())
                        .and(payment.isDeleted.eq(false))
                        .and(reservation.reservationState.eq(ReservationState.RESERVATION_DONE))
                )
                .orderBy(reservation.reserveStartDate.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        List<UserReservationDto> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }

}
