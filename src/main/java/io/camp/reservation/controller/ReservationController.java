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
    @PostMapping()
    public ResponseEntity<ReservationDto> createReservation(@RequestBody ReservationPostDto postDto){
        //동시 예약 시도 체크
        log.info("예약 생성 시작");
        ReservationDto dto = reservationService.createReservation(postDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    //특정 예약 상세 조회
    @GetMapping("/{reservationId}")
    public ResponseEntity<SingleResponseDto<ReservationResponseDto>> getReservation(@PathVariable("reservationId") Long reservationId){
        Reservation reservation = reservationService.findReservation(reservationId);

        ReservationResponseDto responseDto = mapper.reservationToReservationResponseDto(reservation);

        return new ResponseEntity<>(
                new SingleResponseDto<>(responseDto), HttpStatus.OK);
    }

    //회원 예약 내역 조회
    @GetMapping("/user")
    public ResponseEntity<List<ReservationDto>> getReservationByUserSeq(@AuthenticationPrincipal JwtUserDetails userDetails){
        List<ReservationDto> reservations = reservationService.findReservationsByUserId(userDetails.getSeq());

        return ResponseEntity.ok(reservations);
    }

    //예약 수정
    @PatchMapping("/cancel/{reservationSeq}")
    public ResponseEntity<Void> cancelReservation(@PathVariable("reservationSeq") Long reservationSeq){
        reservationService.cancelReservation(reservationSeq);

        return ResponseEntity.ok().build();
    }

    //예약이 해당 사이트에 있는지 확인
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