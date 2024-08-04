package io.camp.campsite.controller;


import io.camp.campsite.model.dto.SeasonDto;
import io.camp.campsite.service.SeasonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/season")
@RequiredArgsConstructor
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





}
