package io.camp.campsite.controller;


import io.camp.campsite.model.dto.SeasonDto;
import io.camp.campsite.model.entity.SeasonType;
import io.camp.campsite.service.SeasonService;
import io.camp.common.dto.SingleResponseDto;
import java.time.LocalDate;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "시즌 정보 생성", description = "시즌 정보를 삽입")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json")),
    })
    @PostMapping
    public ResponseEntity<SeasonDto> insertSeason(@RequestBody SeasonDto seasonDto){
       SeasonDto result = seasonService.insertSeason(seasonDto);
        return ResponseEntity.ok(result);
    }


    @Operation(summary = "campsiteSeq값을 사용해  시즌 정보 불러오기", description = "campsiteSeq을 가진 시즌 정보 가져오기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json")),
    })
    @GetMapping("/campsiteSeq/{campsiteSeq}")
    public ResponseEntity<List<SeasonDto>> getSeasonByCampsiteSeq(@PathVariable("campsiteSeq") long campsiteSeq){
       List<SeasonDto> dtos =seasonService.findSeasonByCampsiteSeq(campsiteSeq);
       return ResponseEntity.ok(dtos);
    }

    @Operation(summary = "seasonSeq을 사용해 시즌 정보 가져오기 ", description = "seasonSeq을 가진 시즌 정보 가져오기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json")),
    })
    @DeleteMapping("/{seasonSeq}")
    public ResponseEntity<Long> deleteSeasonBySeq(@PathVariable("seasonSeq") long seasonSeq){
        seasonService.deleteSeasonBySeq(seasonSeq);
        return ResponseEntity.ok(seasonSeq);
    }

    @Operation(summary = "날짜 범위에 해당하는 시즌타입 불러오기", description = "날짜 범위의 시즌 타입 불러오기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json")),
    })
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
