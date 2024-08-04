package io.camp.campsite.repository;

import io.camp.campsite.model.dto.SiteDto;
import io.camp.campsite.model.entity.Season;
import io.camp.campsite.model.entity.Site;

import java.util.List;

public interface SeasonRepositoryCustom {


    List<Season> findSeasonByCampsiteSeq(long campsiteSeq);


}
