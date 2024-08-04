package io.camp.campsite.repository;


import com.querydsl.jpa.impl.JPAQueryFactory;
import io.camp.campsite.model.dto.SiteDto;
import io.camp.campsite.model.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SeasonRepositoryCustomImpl  implements  SeasonRepositoryCustom{

    private final JPAQueryFactory queryFactory;


    public List<Season> findSeasonByCampsiteSeq(long campsiteSeq){

        return queryFactory.selectFrom(QSeason.season)
                .leftJoin(QSeason.season.campsite, QCampsite.campsite)
                .where(QSeason.season.campsite.seq.eq(campsiteSeq))
                .fetch();

    }




}
