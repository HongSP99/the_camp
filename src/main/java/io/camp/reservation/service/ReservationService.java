package io.camp.reservation.service;

import io.camp.campsite.model.entity.Campsite;
import io.camp.campsite.repository.CampSiteRepository;
import io.camp.reservation.mapper.ReservationMapper;
import io.camp.reservation.model.Reservation;
import io.camp.reservation.model.ReservationState;
import io.camp.reservation.model.dto.ReservationDto;
import io.camp.reservation.model.dto.ReservationPostDto;
import io.camp.reservation.repository.ReservationRepository;
import io.camp.user.model.User;
import io.camp.user.service.AuthService;
import jakarta.transaction.Transactional;
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
    private final AuthService authService;
    private final ReservationMapper mapper;
    private final CampSiteRepository campSiteRepository;

    //새로운 예약을 생성한다.
    public Reservation createReservation(ReservationPostDto requestDto){
        log.info("유저 찾기");
        User user = authService.getVerifiyLoginCurrentUser();
        log.info("유저 찾기 성공");
        Campsite campsite = campSiteRepository.findById(requestDto.getCampsiteId())
                .orElseThrow(() -> new IllegalArgumentException("캠핑장을 찾을 수 없습니다."));
        log.info("campsite 찾기 성공");
        log.info(requestDto.toString());
        Reservation reservation = mapper.reservationPostDtoToReservation(requestDto);
        log.info("예약 생성 성공");
        reservation.setUser(user);
        reservation.setCampsite(campsite);

        log.info(reservation.toString());

        Reservation savedReservation;
        try{
            savedReservation = reservationRepository.save(reservation);
        } catch (Exception e){
            throw new IllegalArgumentException("예약 중 문제 발생");
        }

        return savedReservation;
    }

    public Reservation findReservation(long reservationId) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(reservationId);
        Reservation findReservation = optionalReservation.orElseThrow(() ->
                        new IllegalArgumentException("RESERVATION_NOT_FOUND"));

        return findReservation;
    }
}
