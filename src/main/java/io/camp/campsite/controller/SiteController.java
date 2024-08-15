package io.camp.campsite.controller;


import io.camp.campsite.model.dto.SiteDto;
import io.camp.campsite.service.SiteService;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/site")
@RequiredArgsConstructor
public class SiteController {
    private final SiteService siteService;


    @Operation(summary = "zoneSeq로 zone가져오기 ", description = "zoneSeq을 가진 존 정보 가져오기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json")),
    })
    @GetMapping("/{zoneSeq}")
    public ResponseEntity<List<SiteDto>> getSiteDtosByZone(@PathVariable("zoneSeq") Long zoneSeq){
        List<SiteDto> siteDtos = siteService.getSiteByZone(zoneSeq);

        return ResponseEntity.ok(siteDtos);
    }



}
