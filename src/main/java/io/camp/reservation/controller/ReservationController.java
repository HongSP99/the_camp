package io.camp.reservation.controller;

import io.camp.common.dto.SingleResponseDto;
import io.camp.reservation.mapper.ReservationMapper;
import io.camp.reservation.model.Reservation;
import io.camp.reservation.model.dto.ReservationDto;
import io.camp.reservation.model.dto.ReservationPostDto;
import io.camp.reservation.model.dto.ReservationResponseDto;
import io.camp.reservation.service.ReservationService;
import io.camp.user.jwt.JwtUserDetails;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<SingleResponseDto<?>> getReservation(@PathVariable("reservationId") Long reservationId){
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
    @PostMapping("/cancel/{reservationSeq}")
    public ResponseEntity<Void> cancelReservation(@PathVariable("reservationSeq") Long reservationSeq){
        reservationService.cancelReservation(reservationSeq);

        return ResponseEntity.ok().build();
    }
}