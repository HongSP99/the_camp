package io.camp.campsite.repository;

import io.camp.campsite.model.entity.Campsite;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CampSiteRepositoryCustom {

    Campsite findCampsiteWithAllInfo(long id);

}
