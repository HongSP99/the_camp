package io.camp.reservation.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ReservationRepositoryCustomImpl {
    private final JPAQueryFactory jpaQueryFactory;
}
