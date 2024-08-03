package io.camp.reservation.service;

import io.camp.campsite.model.entity.Campsite;
import io.camp.campsite.model.entity.Site;
import io.camp.campsite.model.entity.Zone;
import io.camp.campsite.repository.CampSiteRepository;
import io.camp.campsite.repository.SiteRepository;
import io.camp.campsite.repository.ZoneRepository;
import io.camp.exception.ExceptionCode;
import io.camp.exception.reservation.ReservationException;
import io.camp.reservation.mapper.ReservationMapper;
import io.camp.reservation.model.Reservation;
import io.camp.reservation.model.ReservationState;
import io.camp.reservation.model.dto.ReservationDto;
import io.camp.reservation.model.dto.ReservationExistenceDto;
import io.camp.reservation.model.dto.ReservationPostDto;
import io.camp.reservation.repository.ReservationRepository;
import io.camp.user.model.User;
import io.camp.user.service.UserService;
import jakarta.transaction.Transactional;
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

        return ReservationDto.fromEntity(reservation);
    }

    //예약 취소
    public void cancelReservation(Long reservationSeq){
        Reservation reservation = findReservation(reservationSeq);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime ReservationStartDate = reservation.getReserveStartDate();

        long DayUntilReservationStart = ChronoUnit.DAYS.between(now, ReservationStartDate);

        if (DayUntilReservationStart <= 1) {
            throw new ReservationException(ExceptionCode.RESERVATION_CANNOT_BE_CANCELLED);
        }

        reservation.setReservationState(ReservationState.CANCEL);
    }

    //유저 예약 내역
    public Reservation findReservation(long reservationId) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(reservationId);
        Reservation findReservation = optionalReservation.orElseThrow(() ->
                        new ReservationException(ExceptionCode.RESERVATION_NOT_FOUND));

        return findReservation;
    }

    public List<ReservationDto> findReservationsByUserId(Long userSeq){
        List<Reservation> reservations = reservationRepository.findAll();
        return reservations.stream()
                .filter(reservation -> reservation.getUser().getSeq().equals(userSeq))
                .map(ReservationDto::fromEntity)
                .collect(Collectors.toList());
    }

    //TODO : 해당 존에 예약이 있는지 확인하는 기능 구현
//    public boolean checkReservationExistence(ReservationExistenceDto existenceDto){
//        List<ReservationState> status = Arrays.asList(
//                ReservationState.RESERVATION_DONE,
//                ReservationState.NO_CANCEL
//        );
//
//        return true;
//    }
}
