package io.camp.campsite.controller;


import io.camp.campsite.service.CampSiteService;
import lombok.AllArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
public class CampsiteController {

    private final CampSiteService campSiteService;

    private  final String uri = "http://apis.data.go.kr/B551011/GoCamping/basedList?serviceKey=5mZ%2FcSX69J3%2Bg2%2FSS77LbWusUy4KO6ZdvX5KuQBk0o5rXPFpJ4jP%2Fcu6DD74kPm5U2WKJco%2FVSxn9DFqFGKRTw%3D%3D&MobileOS=ETC&MobileApp=AppTest";

    @GetMapping("/campingDataOfApi/{pageNumber}")
    public String getTweetsBlocking(@PathVariable("pageNumber") String pageNumber) throws URISyntaxException, UnsupportedEncodingException, ParseException {
        RestTemplate restTemplate = new RestTemplate();
        JSONParser parser = new JSONParser();

        restTemplate.getMessageConverters()
                .add(0,new StringHttpMessageConverter(StandardCharsets.UTF_8));

        ResponseEntity<String> response = restTemplate.getForEntity(new URI(uri+"&pageNo="+pageNumber+"&numOfRows=10&_type=Json"),String.class);

        JSONObject object = (JSONObject) parser.parse(response.getBody());
        object = (JSONObject) object.get("response");
        JSONObject body = (JSONObject) object.get("body");
        JSONObject items = (JSONObject) body.get("items");
        JSONArray itemArray =(JSONArray)items.get("item");

        campSiteService.insertCampsiteFromJson(itemArray);


        return  itemArray.toString();
    }


}
