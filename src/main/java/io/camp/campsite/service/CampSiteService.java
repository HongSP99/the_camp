package io.camp.campsite.service;

import io.camp.campsite.model.dto.CampSiteDto;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.net.URISyntaxException;

public interface CampSiteService {
    JSONArray insertCampsiteFromJson(String pageNumber) throws URISyntaxException, ParseException;
    CampSiteDto getCampsiteBySeq(long seq);
    Page<CampSiteDto> searchCampsites(String name, Pageable pageable);
    Page<CampSiteDto> getAllPaging(int page, int size);
    CampSiteDto getCampsiteWithAllInfo(long id);
    Page<CampSiteDto> searchCampsitesWithPaging(String query, Pageable pageable,String type);
}
