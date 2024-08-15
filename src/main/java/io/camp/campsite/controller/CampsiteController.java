package io.camp.campsite.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import io.camp.campsite.model.dto.PagingDto;
import io.camp.campsite.service.CampSiteService;
import io.camp.campsite.model.dto.CampSiteDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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


    @Operation(summary = "캠핑장 정보 api로 가져오기", description = "캠핑장 정보를 api로 불러와서 db에 저장")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json")),
    })
    @GetMapping("/data/{pageNumber}")
    public String getTweetsBlocking(@PathVariable("pageNumber") String pageNumber) throws URISyntaxException, UnsupportedEncodingException, ParseException, JsonProcessingException {

        return campSiteService.insertCampsiteFromJson(pageNumber);
    }


    @Operation(summary = "캠핑장 쿼리문과 타입으로 검색", description = "캠핑장 정보를 검색 해서 페이징 정보와 함께 리턴")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json")),
    })
    @GetMapping("/")
    public Page<CampSiteDto> searchCampsites(PagingDto pagingDto){
        Pageable pageable = PageRequest.of(pagingDto.getPage(), pagingDto.getSize());
        Page<CampSiteDto> result = campSiteService.searchCampsitesWithPaging(pagingDto.getQuery(),pageable,pagingDto.getType());
        return result;
    }

    @Operation(summary = "모든 캠핑장 검색", description = "모든 캠핑장 정보를 검색 해서 페이징 정보와 함께 리턴")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json")),
    })
    @GetMapping
    public Page<CampSiteDto> getCampsitesWithPaging(@RequestParam(name = "page" ,defaultValue = "0") int page , @RequestParam(name = "size" , defaultValue = "6") int size){
        return campSiteService.getAllPaging(page,size);
    }

    @Operation(summary = "캠핑장 id로 검색", description = "캠핑장 정보를 id로 검색")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json")),
    })
    @GetMapping("/{id}")
    public CampSiteDto getCampsiteById(@PathVariable("id") int id){
        CampSiteDto campSiteDto = campSiteService.getCampsiteBySeq(id);
        System.out.println(campSiteDto);
        return campSiteDto;
    }


    @Operation(summary = "캠핑장 id로 검색 (존 과 구역 정보 포함)", description = "캠핑장 정보를 id로 검색 존과 구역 정보 포함")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json")),
    })
    @GetMapping("/zone/site/{id}")
    public ResponseEntity<CampSiteDto> getCampsiteWithAll(@PathVariable("id") long id){

      CampSiteDto campSiteDto =  campSiteService.getCampsiteWithAllInfo(id);

      return ResponseEntity.ok(campSiteDto);
    }


}
