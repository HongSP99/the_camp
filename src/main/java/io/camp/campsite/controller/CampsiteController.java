package io.camp.campsite.controller;


import io.camp.campsite.service.CampSiteService;
import io.camp.campsite.model.dto.CampSiteDto;
import lombok.AllArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/campsite")
public class CampsiteController {

    private final CampSiteService campSiteService;

    private final String uri = "http://apis.data.go.kr/B551011/GoCamping/basedList?serviceKey=5mZ%2FcSX69J3%2Bg2%2FSS77LbWusUy4KO6ZdvX5KuQBk0o5rXPFpJ4jP%2Fcu6DD74kPm5U2WKJco%2FVSxn9DFqFGKRTw%3D%3D&MobileOS=ETC&MobileApp=AppTest";

    @GetMapping("/data/{pageNumber}")
    public String getTweetsBlocking(@PathVariable("pageNumber") String pageNumber) throws URISyntaxException, UnsupportedEncodingException, ParseException {
        RestTemplate restTemplate = new RestTemplate();
        JSONParser parser = new JSONParser();

        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));

        ResponseEntity<String> response = restTemplate.getForEntity(new URI(uri + "&pageNo=" + pageNumber + "&numOfRows=10&_type=Json"), String.class);

        JSONObject object = (JSONObject) parser.parse(response.getBody());
        object = (JSONObject) object.get("response");
        JSONObject body = (JSONObject) object.get("body");
        JSONObject items = (JSONObject) body.get("items");
        JSONArray itemArray = (JSONArray) items.get("item");

        campSiteService.insertCampsiteFromJson(itemArray);

        return itemArray.toString();
    }

    @GetMapping("/searchCampsites")
    public Page<CampSiteDto> searchCampsites(
            @RequestParam("query") String query,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "9") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return campSiteService.searchCampsites(query, pageable);
    }

    @GetMapping
    public Page<CampSiteDto> getCampsitesWithPaging(@RequestParam(name = "page" ,defaultValue = "0") int page , @RequestParam(name = "size" , defaultValue = "6") int size){
        return campSiteService.getAllPaging(page,size);
    }

    @GetMapping("/{id}")
    public CampSiteDto getCampsiteById(@PathVariable("id") int id){
        return campSiteService.getCampsiteBySeq(id);
    }


    @GetMapping("/zone/site/{id}")
    public ResponseEntity<CampSiteDto> getCampsiteWithAll(@PathVariable("id") long id){

      CampSiteDto campSiteDto =  campSiteService.getCampsiteWithAllInfo(id);

      return ResponseEntity.ok(campSiteDto);
    }


}
