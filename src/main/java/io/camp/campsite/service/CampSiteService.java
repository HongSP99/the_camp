package io.camp.campsite.service;

import io.camp.campsite.model.dto.CampSiteDto;
import org.json.simple.JSONArray;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CampSiteService {
    void insertCampsiteFromJson(JSONArray campsiteArray);
    CampSiteDto getCampsiteBySeq(long seq);
    Page<CampSiteDto> searchCampsites(String name, Pageable pageable);
    Page<CampSiteDto> getAllPaging(int page, int size);
    CampSiteDto getCampsiteWithAllInfo(long id);
    Page<CampSiteDto> getCampsitesByTitleWithPaging(String query, Pageable pageable);
    Page<CampSiteDto> getCampsitesByRegionWithPaging(String query, Pageable pageable);
    Page<CampSiteDto> getCampsitesByThemeWithPaging(String query, Pageable pageable);
}
