package io.camp.campsite.repository;

import io.camp.campsite.model.entity.Campsite;
import org.springframework.data.domain.Page;

public interface CampSiteRepositoryCustom {

    Page<Campsite> findCampsitesByKeyword(String keyword);

}
