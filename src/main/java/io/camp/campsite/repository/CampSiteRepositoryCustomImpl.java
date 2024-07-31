package io.camp.campsite.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.camp.campsite.model.entity.Campsite;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CampSiteRepositoryCustomImpl implements CampSiteRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Campsite> findCampsitesByKeyword(String keyword) {

        return null;
    }
}
