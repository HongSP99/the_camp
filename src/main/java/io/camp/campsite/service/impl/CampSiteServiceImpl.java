package io.camp.campsite.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.google.gson.Gson;
import io.camp.campsite.mapper.CampsiteMapper;
import io.camp.campsite.model.dto.CampSiteDto;
import io.camp.campsite.model.entity.Campsite;
import io.camp.campsite.model.entity.Zone;
import io.camp.campsite.repository.CampSiteRepository;
import io.camp.campsite.service.CampSiteService;
import io.camp.common.exception.Campsite.CampsiteNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

@Service
@Slf4j
@AllArgsConstructor
public class CampSiteServiceImpl implements CampSiteService {
    private final String uri = "http://apis.data.go.kr/B551011/GoCamping/basedList?serviceKey=5mZ%2FcSX69J3%2Bg2%2FSS77LbWusUy4KO6ZdvX5KuQBk0o5rXPFpJ4jP%2Fcu6DD74kPm5U2WKJco%2FVSxn9DFqFGKRTw%3D%3D&MobileOS=ETC&MobileApp=AppTest";

    private final CampSiteRepository campSiteRepository;
    private final CampsiteMapper campsiteMapper;

    @Override
    @Transactional
    public String insertCampsiteFromJson(String pageNumber) throws URISyntaxException, JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));

        ResponseEntity<String> response = restTemplate.getForEntity(new URI(uri + "&pageNo=" + pageNumber + "&numOfRows=10&_type=Json"), String.class);

        JsonNode rootNode = objectMapper.readTree(response.getBody());
        JsonNode responseNode = rootNode.path("response");
        JsonNode bodyNode = responseNode.path("body");
        JsonNode itemsNode = bodyNode.path("items");
        ArrayNode itemArray = (ArrayNode) itemsNode.path("item");


        Gson gson = new Gson();
        itemArray.forEach(item -> {
            CampSiteDto campSite = null;
            try {
                campSite = objectMapper.treeToValue(item, CampSiteDto.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            campSiteRepository.save(campsiteMapper.toCampsiteEntity(campSite));
        });
        log.info("api 데이터가 등록되었습니다.");

        return itemArray.toString();
    }

    @Override
    @Transactional(readOnly = true)
    public CampSiteDto getCampsiteBySeq(long seq) {
        Campsite campsite = campSiteRepository.findById(seq)
                .orElseThrow(() -> new CampsiteNotFoundException("해당 캠핑장이 존재하지 않습니다."));

        System.out.println(campsite.getSeq());
        return campsiteMapper.toCampsiteDto(campsite);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CampSiteDto> searchCampsites(String name, Pageable pageable) {
        Page<Campsite> campsites = campSiteRepository.findByFacltNmContainingIgnoreCase(name, pageable);
        return campsites.map(campsiteMapper::toCampsiteDto);
    }

    @Transactional(readOnly = true)
    public Page<CampSiteDto> getAllPaging(int page, int size){
        Page<Campsite> campsites = campSiteRepository.findAll(PageRequest.of(page,size));
        Page<CampSiteDto> campsiteDtos = campsites.map(campsiteMapper::toCampsiteDto);
        return campsiteDtos;
    }

    @Transactional(readOnly = true)
    public CampSiteDto getCampsiteWithAllInfo(long id){
       Campsite campsite =  campSiteRepository.findCampsiteWithAllInfo(id);
       CampSiteDto campsiteDto = campsiteMapper.toCampsiteDto(campsite);
       campsiteDto.setZones(campsite.getZones().stream().map(Zone::toDto).toList());
       return campsiteDto;
    }

    public Page<CampSiteDto> searchCampsitesWithPaging(String query, Pageable pageable,String type){
        Page<Campsite> campsites = campSiteRepository.searchWithPaging(query,pageable,type);
        Page<CampSiteDto> campSiteDtos = campsites.map(campsiteMapper::toCampsiteDto);
        return campSiteDtos;
    }
}
