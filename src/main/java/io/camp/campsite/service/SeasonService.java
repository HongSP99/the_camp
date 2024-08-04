package io.camp.campsite.service;


import io.camp.campsite.model.dto.SeasonDto;
import io.camp.campsite.model.dto.SiteDto;
import io.camp.campsite.model.entity.Campsite;
import io.camp.campsite.model.entity.Season;
import io.camp.campsite.model.entity.Site;
import io.camp.campsite.repository.CampSiteRepository;
import io.camp.campsite.repository.SeasonRepository;
import io.camp.exception.Campsite.CampsiteNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeasonService {

    private final SeasonRepository seasonRepository;

    private final CampSiteRepository campSiteRepository;


    @Transactional
    public SeasonDto insertSeason(SeasonDto seasonDto){

        Campsite campsite = campSiteRepository.findById(seasonDto.getCampsite())
                .orElseThrow(() -> new CampsiteNotFoundException("해당 캠핑장이 존재하지 않습니다."));
        Season season = seasonDto.toEntity(campsite);

        Season result = seasonRepository.save(season);
        return result.toDto();
    }


    @Transactional(readOnly = true)
    public List<SeasonDto> findSeasonByCampsiteSeq(long campsiteSeq){

      List<Season> seasons =  seasonRepository.findSeasonByCampsiteSeq(campsiteSeq);
      List<SeasonDto> dtos =seasons.stream().map(Season::toDto).toList();

      return dtos;
    }

    @Transactional
    public long deleteSeasonBySeq(long seasonSeq){
        seasonRepository.deleteById(seasonSeq);
        return seasonSeq;
    }




}
