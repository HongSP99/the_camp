package io.camp.reservation.service;

import io.camp.campsite.model.entity.SeasonType;
import io.camp.campsite.model.entity.Site;
import io.camp.campsite.model.entity.Zone;
import io.camp.campsite.repository.SiteRepository;
import io.camp.campsite.repository.ZoneRepository;
import io.camp.campsite.service.SeasonService;
import io.camp.campsite.service.ZoneService;
import io.camp.common.exception.ExceptionCode;
import io.camp.common.exception.reservation.ReservationException;
import io.camp.reservation.mapper.ReservationMapper;
import io.camp.reservation.model.Reservation;
import io.camp.reservation.model.ReservationState;
import io.camp.reservation.model.dto.ReservationDto;
import io.camp.reservation.model.dto.ReservationExistenceDto;
import io.camp.reservation.model.dto.ReservationPostDto;
import io.camp.reservation.repository.ReservationRepository;
import io.camp.user.model.User;
import io.camp.user.service.UserService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final UserService authService;
    private final ReservationMapper mapper;
    private final SiteRepository siteRepository;

    //새로운 예약을 생성한다.
    public ReservationDto createReservation(ReservationPostDto requestDto){
        log.info("유저 찾기");
        User user = authService.getVerifiyLoginCurrentUser();
        log.info("유저 찾기 성공");
        Site site = siteRepository.findById(requestDto.getSiteSeq())
                .orElseThrow(() -> new IllegalArgumentException("캠핑장을 찾을 수 없습니다."));
        log.info("campsite 찾기 성공");
        log.info(requestDto.toString());

        Reservation reservation = mapper.reservationPostDtoToReservation(requestDto);
        log.info("예약 생성 성공");
        reservation.setUser(user);
        reservation.setSite(site);

        log.info(reservation.toString());

        Reservation savedReservation;
        try{
            savedReservation = reservationRepository.save(reservation);
        } catch (Exception e){
            throw new ReservationException(ExceptionCode.RESERVATION_NOT_FOUND);
        }

        return ReservationDto.fromEntity(savedReservation);
    }

    //예약 취소
    public void cancelReservation(Long reservationSeq){
        Reservation reservation = findReservation(reservationSeq);

        LocalDate now = LocalDate.now();
        LocalDate ReservationStartDate = reservation.getReserveStartDate();

        long DayUntilReservationStart = ChronoUnit.DAYS.between(now, ReservationStartDate);

        if (DayUntilReservationStart <= 1) {
            throw new ReservationException(ExceptionCode.RESERVATION_CANNOT_BE_CANCELLED);
        }

        reservation.setReservationState(ReservationState.CANCEL);
        reservationRepository.save(reservation);
    }


    public Reservation findReservation(long reservationId) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(reservationId);
        Reservation findReservation = optionalReservation.orElseThrow(() ->
                        new ReservationException(ExceptionCode.RESERVATION_NOT_FOUND));

        return findReservation;
    }

    //유저 예약 내역
    public List<ReservationDto> findReservationsByUserId(Long userSeq){
        List<Reservation> reservations = reservationRepository.findAll();
        return reservations.stream()
                .filter(reservation -> reservation.getUser().getSeq().equals(userSeq))
                .map(ReservationDto::fromEntity)
                .collect(Collectors.toList());
    }

    //예약 있는지 각 사이트 마다 확인
    public boolean checkReservationExistence(ReservationExistenceDto existenceDto){
        return reservationRepository.checkReservationExistence(existenceDto);
    }

    //가격 검증
//    public int calculationTotalPrice(Long zoneSeq, int adults, LocalDate start, LocalDate end){
//        //어른 기본 2명에서 1명 추가 당 10,000원, season별로 가격이 다름.
//        Zone zone = zoneService.findZoneById(zoneSeq);
//
//        SeasonType seasonType = seasonService.getSeasonTypeByDateRange(start, end);
//
//        int seasonPrice;
//        if(seasonType.equals(SeasonType.BEST_PEAK)){
//            seasonPrice = zone.getBestPeakSeasonPrice();
//        } else if(seasonType.equals(SeasonType.PEAK)){
//            seasonPrice = zone.getPeakSeasonPrice();
//        } else {
//            seasonPrice = zone.getOffSeasonPrice();
//        }
//
//        if(adults > 2){
//            seasonPrice += (adults - 2) * 10000;
//        }
//
//        return seasonPrice;
//    }
}
