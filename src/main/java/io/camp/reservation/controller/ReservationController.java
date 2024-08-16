package io.camp.reservation.controller;

import io.camp.common.dto.SingleResponseDto;
import io.camp.reservation.mapper.ReservationMapper;
import io.camp.reservation.model.Reservation;
import io.camp.reservation.model.dto.ReservationDto;
import io.camp.reservation.model.dto.ReservationExistenceDto;
import io.camp.reservation.model.dto.ReservationPostDto;
import io.camp.reservation.model.dto.ReservationResponseDto;
import io.camp.reservation.service.ReservationService;
import io.camp.user.jwt.JwtUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservation")
@Slf4j
public class ReservationController {
    private final ReservationService reservationService;
    private final ReservationMapper mapper;




    //예약 생성
    @Operation(summary = "예약 생성", description = "예약을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "예약이 생성되었습니다."),
    })
    @PostMapping()
    public ResponseEntity<SingleResponseDto<ReservationDto>> createReservation(@RequestBody ReservationPostDto postDto){
        //동시 예약 시도 체크
        log.info("예약 생성 시작");
        ReservationDto dto = reservationService.createReservation(postDto);
        return new ResponseEntity<>(
                new SingleResponseDto<>(dto), HttpStatus.CREATED);
    }

    //특정 예약 상세 조회
    @Operation(summary = "특정 예약 상세 조회", description = "예약을 상세 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "예약이 조회되었습니다."),
            @ApiResponse(responseCode = "404", description = "예약을 찾을 수 없습니다."),
    })
    @GetMapping("/{reservationId}")
    public ResponseEntity<SingleResponseDto<ReservationResponseDto>> getReservation(@PathVariable("reservationId") Long reservationId){
        Reservation reservation = reservationService.findReservation(reservationId);

        ReservationResponseDto responseDto = mapper.reservationToReservationResponseDto(reservation);

        return new ResponseEntity<>(
                new SingleResponseDto<>(responseDto), HttpStatus.OK);
    }

    //회원 예약 내역 조회
    @Operation(summary = "유저 예약 조회", description = "유저의 예약을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "유저의 예약이 조회되었습니다."),
            @ApiResponse(responseCode = "404", description = "유저를 찾을 수 없습니다."),
    })
    @GetMapping("/user")
    public ResponseEntity<SingleResponseDto<List<ReservationDto>>> getReservationByUserSeq(@AuthenticationPrincipal JwtUserDetails userDetails){
        List<ReservationDto> reservations = reservationService.findReservationsByUserId(userDetails.getSeq());

        return new ResponseEntity<>(
                new SingleResponseDto<>(reservations), HttpStatus.OK);
    }

    //예약 취소
    @Operation(summary = "예약 취소", description = "예약을 취소합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "예약이 취소되었습니다."),
            @ApiResponse(responseCode = "404", description = "예약을 찾을 수 없습니다."),
    })
    @PatchMapping("/cancel/{reservationSeq}")
    public ResponseEntity<Void> cancelReservation(@PathVariable("reservationSeq") Long reservationSeq){
        reservationService.cancelReservation(reservationSeq);

        return ResponseEntity.ok().build();
    }

    //예약이 해당 사이트에 있는지 확인
    @Operation(summary = "예약이 존재하는지 조회", description = "해당 날짜에 해당 사이트가 예약이 존재하는지 확인합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "예약이 있느지 없는지 확인되었습니다."),
    })
    @PostMapping("/existence")
    public ResponseEntity<SingleResponseDto<ReservationExistenceDto>> checkReservationExistence(@RequestBody ReservationExistenceDto existenceDto){
        log.info("체크 시작");
        boolean existence = reservationService.checkReservationExistence(existenceDto);
        log.info("체크 끝");
        existenceDto.setExistence(existence);
        log.info(existenceDto.toString());

        return new ResponseEntity<>(
                new SingleResponseDto<>(existenceDto), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<ReservationResponseDto>> getAllReservationsWithPaging(@RequestParam(value = "page", defaultValue = "0") int page,
                                                               @RequestParam(value = "size", defaultValue = "10") int size){
        Page<ReservationResponseDto> dtos = reservationService.findAllReservationsWithPaging(page,size);
        return ResponseEntity.ok(dtos);
    }

    @PatchMapping
    public ResponseEntity<ReservationDto> updateReservation(@RequestBody ReservationDto dto){
        System.out.println("patch");
        reservationService.updateReservation(dto);
        return ResponseEntity.ok(dto);
    }

}