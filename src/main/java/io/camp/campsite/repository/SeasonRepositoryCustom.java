package io.camp.campsite.repository;

import io.camp.campsite.model.dto.SiteDto;
import io.camp.campsite.model.entity.Season;
import io.camp.campsite.model.entity.SeasonType;
import io.camp.campsite.model.entity.Site;

import java.time.LocalDate;
import java.util.List;

public interface SeasonRepositoryCustom {


    List<Season> findSeasonByCampsiteSeq(long campsiteSeq);

    Long findDuplicatedSeason(long campsiteSeq, LocalDate start, LocalDate end);

    SeasonType findSeasonTypeByDateRange(LocalDate startDate, LocalDate endDate);

    List<SeasonType> findSeasonTypeByReservationEndDate(LocalDate startDate, LocalDate endDate, Long campSiteSeq);
}
