package io.camp.campsite.service.impl;

import com.google.gson.Gson;
import io.camp.campsite.mapper.CampsiteMapper;
import io.camp.campsite.model.dto.CampSiteDto;
import io.camp.campsite.model.entity.Campsite;
import io.camp.campsite.repository.CampSiteRepository;
import io.camp.campsite.service.CampSiteService;
import io.camp.exception.Campsite.CampsiteNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class CampSiteServiceImpl implements CampSiteService {

    private final CampSiteRepository campSiteRepository;
    private final CampsiteMapper campsiteMapper;

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

    @Override
    @Transactional(readOnly = true)
    public CampSiteDto getCampsiteBySeq(long seq) {
        Campsite campsite = campSiteRepository.findById(seq)
                .orElseThrow(() -> new CampsiteNotFoundException("해당 캠핑장이 존재하지 않습니다."));
        return campsiteMapper.toCampsiteDto(campsite);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CampSiteDto> searchCampsites(String name, Pageable pageable) {
        Page<Campsite> campsites = campSiteRepository.findByFacltNmContainingIgnoreCase(name, pageable);
        return campsites.map(campsiteMapper::toCampsiteDto);
    }
}
