package io.camp.campsite.controller;


import io.camp.campsite.model.dto.SeasonDto;
import io.camp.campsite.model.entity.SeasonType;
import io.camp.campsite.service.SeasonService;
import io.camp.common.dto.SingleResponseDto;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/season")
@RequiredArgsConstructor
@Slf4j
public class SeasonController {

    private final SeasonService seasonService;

    @PostMapping
    public ResponseEntity<SeasonDto> insertSeason(@RequestBody SeasonDto seasonDto){
       SeasonDto result = seasonService.insertSeason(seasonDto);
        return ResponseEntity.ok(result);
    }


    @GetMapping("/campsiteSeq/{campsiteSeq}")
    public ResponseEntity<List<SeasonDto>> getSeasonByCampsiteSeq(@PathVariable("campsiteSeq") long campsiteSeq){
       List<SeasonDto> dtos =seasonService.findSeasonByCampsiteSeq(campsiteSeq);
       return ResponseEntity.ok(dtos);
    }

    @DeleteMapping("/{seasonSeq}")
    public ResponseEntity<Long> deleteSeasonBySeq(@PathVariable("seasonSeq") long seasonSeq){
        seasonService.deleteSeasonBySeq(seasonSeq);
        return ResponseEntity.ok(seasonSeq);
    }

    @GetMapping("/reserve/{campsiteSeq}")
    public ResponseEntity<SingleResponseDto<SeasonType>> getSeasonType(@PathVariable("campsiteSeq") Long campsiteSeq,
                                                                       @RequestParam("start") LocalDate start,
                                                                       @RequestParam("end") LocalDate end) {
        log.info("getSeasonType");
        SeasonType seasonType = seasonService.getSeasonTypeByDateRange(start, end, campsiteSeq);

        log.info(seasonType.name());
        return new ResponseEntity<>(
                new SingleResponseDto<>(seasonType), HttpStatus.OK);
    }
}
