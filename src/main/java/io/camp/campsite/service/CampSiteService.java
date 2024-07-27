package io.camp.campsite.service;

import io.camp.campsite.model.dto.CampSiteDto;
import org.json.simple.JSONArray;
import org.springframework.transaction.annotation.Transactional;

public interface CampSiteService {

    void insertCampsiteFromJson(JSONArray campsiteArray);

    CampSiteDto getCampsiteBySeq(long seq);
}
