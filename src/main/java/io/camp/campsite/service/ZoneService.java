package io.camp.campsite.service;


import io.camp.campsite.model.dto.ZoneDto;
import io.camp.campsite.model.entity.Campsite;
import io.camp.campsite.model.entity.Zone;
import io.camp.campsite.repository.CampSiteRepository;
import io.camp.campsite.repository.ZoneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ZoneService {

    private final ZoneRepository zoneRepository;

    private final CampSiteRepository campSiteRepository;

    @Transactional
    public Zone insertZone(ZoneDto zoneDto){
        Zone zone = zoneDto.toEntity();
        Campsite campsite = campSiteRepository.findById(zoneDto.getCampSite()).get();
        zone.setCampsite(campsite);
        return zoneRepository.save(zone);
    }



    @Transactional
    public Long deleteZoneBySeq(Long seq){
         zoneRepository.deleteById(seq);
         return seq;
    }

    public ZoneDto getZone(Long zoneSeq){
        Zone zone = zoneRepository.findById(zoneSeq)
                .orElseThrow(() -> new IllegalArgumentException("존을 찾을 수 없다."));

        return ZoneDto.fromEntity(zone);
    }
}
