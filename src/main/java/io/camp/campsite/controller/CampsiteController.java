package io.camp.campsite.controller;


import io.camp.campsite.model.dto.PagingDto;
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



    @GetMapping("/data/{pageNumber}")
    public String getTweetsBlocking(@PathVariable("pageNumber") String pageNumber) throws URISyntaxException, UnsupportedEncodingException, ParseException {




        return campSiteService.insertCampsiteFromJson(pageNumber).toJSONString();
    }

    @GetMapping("/")
    public Page<CampSiteDto> searchCampsites(PagingDto pagingDto){
        Pageable pageable = PageRequest.of(pagingDto.getPage(), pagingDto.getSize());
        Page<CampSiteDto> result = campSiteService.searchCampsitesWithPaging(pagingDto.getQuery(),pageable,pagingDto.getType());
        return result;
    }

    @GetMapping
    public Page<CampSiteDto> getCampsitesWithPaging(@RequestParam(name = "page" ,defaultValue = "0") int page , @RequestParam(name = "size" , defaultValue = "6") int size){
        return campSiteService.getAllPaging(page,size);
    }

    @GetMapping("/{id}")
    public CampSiteDto getCampsiteById(@PathVariable("id") int id){
        CampSiteDto campSiteDto = campSiteService.getCampsiteBySeq(id);
        System.out.println(campSiteDto);
        return campSiteDto;
    }


    @GetMapping("/zone/site/{id}")
    public ResponseEntity<CampSiteDto> getCampsiteWithAll(@PathVariable("id") long id){

      CampSiteDto campSiteDto =  campSiteService.getCampsiteWithAllInfo(id);

      return ResponseEntity.ok(campSiteDto);
    }


}
