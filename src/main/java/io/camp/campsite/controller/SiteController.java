package io.camp.campsite.controller;


import io.camp.campsite.model.dto.SiteDto;
import io.camp.campsite.service.SiteService;
import java.util.List;
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

    @GetMapping("/{zoneSeq}")
    public ResponseEntity<List<SiteDto>> getSiteDtosByZone(@PathVariable("zoneSeq") Long zoneSeq){
        List<SiteDto> siteDtos = siteService.getSiteByZone(zoneSeq);

        return ResponseEntity.ok(siteDtos);
    }



}
