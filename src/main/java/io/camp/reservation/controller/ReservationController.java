package io.camp.reservation.controller;

import io.camp.reservation.mapper.ReservationMapper;
import io.camp.reservation.model.Reservation;
import io.camp.reservation.model.dto.ReservationPostDto;
import io.camp.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservation")
@Slf4j
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<?> createReservation(@RequestBody ReservationPostDto postDto){
        //동시 예약 시도 체크
        try{
            log.info("예약 생성 시작");
            Reservation reservation = reservationService.createReservation(postDto);
            return new ResponseEntity<>(reservation, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>("동시 예약이 발생했습니다. 잠시 후 다시 시도해주세요.", HttpStatus.CONFLICT);
        }
    }
}