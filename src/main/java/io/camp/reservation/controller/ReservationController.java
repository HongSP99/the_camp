package io.camp.reservation.controller;

import io.camp.reservation.mapper.ReservationMapper;
import io.camp.reservation.model.Reservation;
import io.camp.reservation.model.dto.ReservationDto;
import io.camp.reservation.model.dto.ReservationPostDto;
import io.camp.reservation.model.dto.ReservationResponseDto;
import io.camp.reservation.service.ReservationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class ReservationController {
    private final ReservationService reservationService;
    private final ReservationMapper mapper;

    //예약 생성
    @PostMapping("/reservation")
    public ResponseEntity<Object> createReservation(@RequestBody ReservationPostDto postDto){
        //동시 예약 시도 체크
        try{
            log.info("예약 생성 시작");
            Reservation reservation = reservationService.createReservation(postDto);
            return new ResponseEntity<>(reservation, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>("동시 예약이 발생했습니다. 잠시 후 다시 시도해주세요.", HttpStatus.CONFLICT);
        }
    }

    //특정 예약 상세 조회
    @GetMapping("/reservation/{reservation_id}")
    public ResponseEntity<ReservationResponseDto> getReservation(@PathVariable("reservation_id") Long reservationId){
        Reservation reservation = reservationService.findReservation(reservationId);

        ReservationResponseDto responseDto = mapper.reservationToReservationResponseDto(reservation);

        return ResponseEntity.ok(responseDto);
    }

    //회원 예약 내역 조회
    @GetMapping("/reservation/{user_seq}")
    public ResponseEntity<List<ReservationDto>> getReservationByUserSeq(@PathVariable("user_seq") Long userSeq){
        List<ReservationDto> reservations = reservationService.findReservationsByUserId(userSeq);

        return ResponseEntity.ok(reservations);
    }
}