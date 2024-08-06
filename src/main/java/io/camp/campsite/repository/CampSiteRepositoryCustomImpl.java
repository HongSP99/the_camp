package io.camp.campsite.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.camp.campsite.model.entity.*;

import static io.camp.campsite.model.entity.QCampsite.campsite;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class CampSiteRepositoryCustomImpl implements CampSiteRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Campsite findCampsiteWithAllInfo(long id) {
        QCampsite campsite = QCampsite.campsite;
        QZone zone = QZone.zone;
        QSite site = QSite.site;

        Campsite result = queryFactory
                .selectFrom(campsite)
                .leftJoin(campsite.zones,zone)
                .leftJoin(zone.sites,site)
                .where(campsite.seq.eq(id))
                .fetchOne();


        return result;
    }

    @Override
    public Page<Campsite> findCampsitesByTitleWithPaging(String query, Pageable pageable) {
        List<Campsite> fetch = queryFactory.selectFrom(campsite)
                .where(campsite.facltNm.contains(query))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = queryFactory
                .select(campsite.count())
                .from(campsite)
                .where(campsite.facltNm.contains(query))
                .fetchOne();

        return new PageImpl<>(fetch,pageable,count);
    }

    @Override
    public Page<Campsite> findCampsitesByRegionWithPaging(String query, Pageable pageable) {
        List<Campsite> fetch = queryFactory.selectFrom(campsite)
                .where(campsite.doNm.contains(query).or(campsite.sigunguNm.contains(query)
                        .or(campsite.addr1.contains(query))))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = queryFactory
                .select(campsite.count())
                .from(campsite)
                .where(campsite.doNm.contains(query).or(campsite.sigunguNm.contains(query)
                        .or(campsite.addr1.contains(query))))
                .fetchOne();

        return new PageImpl<>(fetch,pageable,count);
    }


}
