package io.camp.campsite.controller;


import io.camp.campsite.model.dto.SiteDto;
import io.camp.campsite.model.dto.ZoneDto;
import io.camp.campsite.model.entity.Zone;
import io.camp.campsite.service.CampSiteService;
import io.camp.campsite.service.SiteService;
import io.camp.campsite.service.ZoneService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/zone")
public class ZoneController {

    private final ZoneService zoneService;

    private final SiteService siteService;

    @Operation(summary = "존 정보 삽입", description = "zone정보 삽입 하기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json")),
    })
    @PostMapping
    public ResponseEntity<ZoneDto> insertZone(@RequestBody ZoneDto zoneDto){
            System.out.println(zoneDto);
            Zone zone = zoneService.insertZone(zoneDto);
            List<SiteDto> sites =siteService.insertSites(zone,zoneDto.getNumOfSite());
            zoneDto.setSites(sites);
            zoneDto.setSeq(zone.getSeq());
            return new ResponseEntity<>(zoneDto , HttpStatus.CREATED);
    }


    @Operation(summary = "존 삭제 ", description = "zoneSeq로 존 삭제 ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json")),
    })
    @DeleteMapping("/{seq}")
    public ResponseEntity<Long> deleteZone(@PathVariable("seq") long seq){
        System.out.println(zoneService.deleteZoneBySeq(seq));
        return ResponseEntity.ok(seq);
    }

    @Operation(summary = "존 불러오기 ", description = "zoneSeq로 존 불러오기 ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json")),
    })
    @GetMapping("/{zoneSeq}")
    public ResponseEntity<ZoneDto> getZone(@PathVariable("zoneSeq") Long zoneSeq){
        ZoneDto dto = zoneService.getZone(zoneSeq);
        return ResponseEntity.ok(dto);
    }


}
