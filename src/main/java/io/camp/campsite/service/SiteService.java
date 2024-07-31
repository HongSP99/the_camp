package io.camp.campsite.service;

import io.camp.campsite.model.entity.Site;
import io.camp.campsite.model.entity.Zone;
import io.camp.campsite.repository.SiteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SiteService {

    private final SiteRepository siteRepository;


    @Transactional
    public int insertSites(Zone zone,int numOfSites){

        for(int i=1;i<=numOfSites;i++){
            siteRepository.save(new Site(i,zone));
        }

        return numOfSites;
    }

}
