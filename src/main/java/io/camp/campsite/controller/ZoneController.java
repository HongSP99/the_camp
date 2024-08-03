package io.camp.campsite.controller;


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

@RestController
@RequiredArgsConstructor
@RequestMapping("/zone")
public class ZoneController {

    private final ZoneService zoneService;

    private final SiteService siteService;

    @PostMapping
    public ResponseEntity<Long> insertZone(@RequestBody ZoneDto zoneDto){
            System.out.println(zoneDto);
            Zone zone = zoneService.insertZone(zoneDto);
            siteService.insertSites(zone,zoneDto.getNumOfSite());
            return new ResponseEntity<>(zone.getSeq() , HttpStatus.CREATED);
    }


    @DeleteMapping("/{seq}")
    public ResponseEntity<Long> deleteZone(@PathVariable("seq") Long seq){
        zoneService.deleteZoneBySeq(seq);
        return ResponseEntity.ok(seq);
    }




}
