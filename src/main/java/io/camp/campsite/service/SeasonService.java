package io.camp.campsite.service;


import io.camp.campsite.model.dto.SeasonDto;
import io.camp.campsite.model.entity.Campsite;
import io.camp.campsite.model.entity.Season;
import io.camp.campsite.model.entity.SeasonType;
import io.camp.campsite.repository.CampSiteRepository;
import io.camp.campsite.repository.SeasonRepository;
import io.camp.common.exception.Campsite.CampsiteNotFoundException;
import io.camp.common.exception.Campsite.SeasonAlreadyExsistException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SeasonService {

    private final SeasonRepository seasonRepository;

    private final CampSiteRepository campSiteRepository;


    @Transactional
    public SeasonDto insertSeason(SeasonDto seasonDto){

        Campsite campsite = campSiteRepository.findById(seasonDto.getCampsite())
                .orElseThrow(() -> new CampsiteNotFoundException("해당 캠핑장이 존재하지 않습니다."));
        Season season = seasonDto.toEntity(campsite);

        Long duplicatedCount = seasonRepository.findDuplicatedSeason(seasonDto.getCampsite(),seasonDto.getStart(),seasonDto.getEnd());

        if(duplicatedCount>0){
            log.info("중복된 시즌이 있습니다. "+duplicatedCount);
            throw new SeasonAlreadyExsistException("중복된 시즌이 이미 있습니다.");
        }

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

    public SeasonType getSeasonTypeByDateRange(LocalDateTime start, LocalDateTime end, Long campsiteId) {
        List<SeasonDto> seasonByCampsite = findSeasonByCampsiteSeq(campsiteId);
        for (SeasonDto season : seasonByCampsite) {
            LocalDateTime sStart = season.getStart().atTime(0, 0, 0);
            LocalDateTime sEnd = season.getEnd().atTime(23, 59, 59);

            if (start.isAfter(sStart) && end.isBefore(sEnd)) {
                return season.getType();
            }
        }
        return SeasonType.NORMAL;
    }
}
