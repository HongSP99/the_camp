package io.camp.campsite.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.camp.campsite.model.entity.*;

import static io.camp.campsite.model.entity.QCampsite.campsite;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
                .leftJoin(campsite.zones).fetchJoin()
                .where(campsite.seq.eq(id))
                .fetchOne();

        List<Long> zoneSeqs = result.getZones().stream().map(Zone::getSeq).toList();

        queryFactory
                .selectFrom(zone)
                .leftJoin(zone.sites,site)
                .fetchJoin()
                .where(zone.seq.in(zoneSeqs))
                .fetch();

        return result;
    }
}
