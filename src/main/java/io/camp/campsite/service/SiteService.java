package io.camp.campsite.service;

import io.camp.campsite.model.dto.SiteDto;
import io.camp.campsite.model.entity.Site;
import io.camp.campsite.model.entity.Zone;
import io.camp.campsite.repository.SiteRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SiteService {

    private final SiteRepository siteRepository;


    @Transactional
    public List<SiteDto> insertSites(Zone zone,int numOfSites){
        List<SiteDto> dtos = new ArrayList<>();
        for(int i=1;i<=numOfSites;i++){

            SiteDto dto = siteRepository.save(new Site(zone.getTitle()+"-"+i,zone)).toDto();
            dtos.add(dto);
        }

        return dtos;
    }

    public List<SiteDto> getSiteByZone(Long zoneSeq){
        return siteRepository.findByZoneSeq(zoneSeq).stream()
                .filter(site -> site.getZone().getSeq().equals(zoneSeq))
                .map(SiteDto::fromEntity)
                .collect(Collectors.toList());
    }
}
