package io.camp.reservation.service;

import io.camp.reservation.mapper.ReservationMapper;
import io.camp.reservation.model.Reservation;
import io.camp.reservation.model.dto.ReservationPostDto;
import io.camp.reservation.repository.ReservationRepository;
import io.camp.user.model.User;
//import io.camp.user.service.AuthService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    //private final AuthService authService;
    private final ReservationMapper mapper;

    //새로운 예약을 생성한다.
    public Reservation createReservation(ReservationPostDto requestDto){
        //User user = authService.getVerifiyLoginCurrentUser();
//        Campsite campsite = campsiteRepository.findById(requestDto.getCampsiteId());

        Reservation reservation = mapper.reservationPostDtoToReservation(requestDto);
        //reservation.setUser(user);
//        reservation.setCampsite(Campsite);

        Reservation savedReservation;
        try{
            savedReservation = reservationRepository.save(reservation);
        } catch (Exception e){
            throw new IllegalArgumentException("예약 중 문제 발생");
        }

        return savedReservation;
    }
}
