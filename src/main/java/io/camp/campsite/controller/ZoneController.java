package io.camp.campsite.controller;


import io.camp.campsite.model.dto.SiteDto;
import io.camp.campsite.model.dto.ZoneDto;
import io.camp.campsite.model.entity.Zone;
import io.camp.campsite.service.CampSiteService;
import io.camp.campsite.service.SiteService;
import io.camp.campsite.service.ZoneService;
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

    @PostMapping
    public ResponseEntity<ZoneDto> insertZone(@RequestBody ZoneDto zoneDto){
            System.out.println(zoneDto);
            Zone zone = zoneService.insertZone(zoneDto);
            List<SiteDto> sites =siteService.insertSites(zone,zoneDto.getNumOfSite());
            zoneDto.setSites(sites);
            zoneDto.setSeq(zone.getSeq());
            return new ResponseEntity<>(zoneDto , HttpStatus.CREATED);
    }


    @DeleteMapping("/{seq}")
    public ResponseEntity<Long> deleteZone(@PathVariable("seq") long seq){
        System.out.println(zoneService.deleteZoneBySeq(seq));
        return ResponseEntity.ok(seq);
    }

    @GetMapping("/{zoneSeq}")
    public ResponseEntity<ZoneDto> getZone(@PathVariable("zoneSeq") Long zoneSeq){
        ZoneDto dto = zoneService.getZone(zoneSeq);
        return ResponseEntity.ok(dto);
    }


}
