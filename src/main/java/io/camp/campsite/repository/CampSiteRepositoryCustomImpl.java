package io.camp.campsite.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.camp.campsite.model.entity.Campsite;
import io.camp.campsite.model.entity.QCampsite;
import static io.camp.campsite.model.entity.QCampsite.campsite;
import io.camp.campsite.model.entity.QSite;
import io.camp.campsite.model.entity.QZone;
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

        return queryFactory
                .selectFrom(campsite)
                .where(campsite.seq.eq(id))
                .fetchOne();
    }
}
