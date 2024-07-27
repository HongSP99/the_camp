package io.camp.campsite.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CampSiteRepositoryCustomImpl implements CampSiteRepositoryCustom{

    private final JPAQueryFactory queryFactory;
}
