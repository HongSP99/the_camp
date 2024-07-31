package io.camp.campsite.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ZoneRepositoryCustomImpl implements ZoneRepositoryCustom{

    private final JPAQueryFactory queryFactory;
}
