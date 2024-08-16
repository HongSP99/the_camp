package io.camp.campsite.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.camp.campsite.model.entity.*;




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
    public Page<Campsite> searchWithPaging(String query, Pageable pageable, String type) {

        BooleanBuilder builder = new BooleanBuilder();
        QCampsite campsite = QCampsite.campsite;

        switch(type){
            case "title":
                builder.and(campsite.facltNm.contains(query));
                break;
            case "region":
                builder.and(campsite.doNm.contains(query).or(campsite.sigunguNm.contains(query)
                        .or(campsite.addr1.contains(query))));
                break;
            case "theme":
                builder.and(campsite.posblFcltyCl.contains(query).or(campsite.sbrsCl.contains(query)));
                break;
            default:
        }

        List<Campsite> fetch = queryFactory.selectFrom(campsite)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = queryFactory
                .select(campsite.count())
                .from(campsite)
                .where(builder)
                .fetchOne();

        return new PageImpl<>(fetch,pageable,count);
    }
}
