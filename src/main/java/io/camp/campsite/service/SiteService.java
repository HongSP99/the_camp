package io.camp.campsite.service;

import io.camp.campsite.model.dto.SiteDto;
import io.camp.campsite.model.entity.Site;
import io.camp.campsite.model.entity.Zone;
import io.camp.campsite.repository.SiteRepository;
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

}
