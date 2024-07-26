package io.camp.campsite.service.impl;

import com.google.gson.Gson;
import io.camp.campsite.model.entity.CampSite;
import io.camp.campsite.repository.CampSiteRepository;
import io.camp.campsite.service.CampSiteService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class CampSiteServiceImpl implements CampSiteService {

    private final CampSiteRepository campSiteRepository;

    @Override
    public void insertCampsiteFromJson(JSONArray campsiteArray) {
        Gson gson = new Gson();
        campsiteArray.forEach(item -> {
            JSONObject object = (JSONObject) item;
            CampSite campSite = gson.fromJson(object.toJSONString(),CampSite.class);
            campSiteRepository.save(campSite);
        });
        log.info("api 데이터가 등록되었습니다.");
    }
}
