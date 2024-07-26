package io.camp.campsite.service.impl;

import com.google.gson.Gson;
import io.camp.campsite.model.entity.Campsite;
import io.camp.campsite.repository.CampSiteRepository;
import io.camp.campsite.service.CampSiteService;
import jakarta.transaction.Transactional;
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
    @Transactional
    public void insertCampsiteFromJson(JSONArray campsiteArray) {
        Gson gson = new Gson();
        campsiteArray.forEach(item -> {
            JSONObject object = (JSONObject) item;
            Campsite campSite = gson.fromJson(object.toJSONString(), Campsite.class);
            campSiteRepository.save(campSite);
        });
        log.info("api 데이터가 등록되었습니다.");
    }
}
