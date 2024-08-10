package io.camp.payment.service;


import com.google.gson.Gson;
import io.camp.campsite.model.dto.ZoneDto;
import io.camp.campsite.model.entity.SeasonType;
import io.camp.campsite.model.entity.Site;
import io.camp.campsite.model.entity.Zone;
import io.camp.campsite.service.SeasonService;
import io.camp.campsite.service.SiteService;
import io.camp.campsite.service.ZoneService;
import io.camp.common.exception.ExceptionCode;
import io.camp.common.exception.payment.PaymentException;
import io.camp.payment.model.Payment;
import io.camp.payment.model.PaymentCancellation;
import io.camp.payment.model.PaymentType;
import io.camp.payment.model.dto.PaymentCancelPostDto;
import io.camp.payment.model.dto.PaymentPostDto;
import io.camp.payment.repository.PaymentCancellationRepository;
import io.camp.payment.repository.PaymentRepository;
import io.camp.reservation.model.Reservation;
import io.camp.reservation.model.dto.ReservationPostDto;
import io.camp.reservation.service.ReservationService;
import io.camp.user.jwt.JwtUserDetails;
import io.camp.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;


@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentCancellationRepository paymentCancellationRepository;
    private final ReservationService reservationService;
    private final ZoneService zoneService;
    private final SeasonService seasonService;
    private final SiteService siteService;

    private static ReservationPostDto getReservationPostDto(PaymentPostDto paymentPostDto, int reservationTotalPrice) {
        ReservationPostDto reservationPostDto = new ReservationPostDto();
        reservationPostDto.setSiteSeq(paymentPostDto.getSiteSeq());
        reservationPostDto.setReserveStartDate(paymentPostDto.getReserveStartDate());
        reservationPostDto.setReserveEndDate(paymentPostDto.getReserveEndDate());
        reservationPostDto.setAdults(paymentPostDto.getAdults());
        reservationPostDto.setChildren(paymentPostDto.getChildren());
        reservationPostDto.setTotalPrice(reservationTotalPrice);
        return reservationPostDto;
    }

    public int calculationTotalPrice(PaymentPostDto dto){
        Site site = siteService.getSiteBySeq(dto.getSiteSeq());
        Long zoneSeq = site.getZone().getSeq();
        ZoneDto zone = zoneService.getZone(zoneSeq);
        Long campsiteSeq = zone.getCampSite();

        log.info("zoneSeq = {}", zoneSeq);
        log.info("campsiteSeq = {}", campsiteSeq);

        SeasonType seasonType =
                seasonService.getSeasonTypeByDateRange(dto.getReserveStartDate(),
                        dto.getReserveEndDate(),
                        campsiteSeq
                );

        LocalDate startDay = dto.getReserveStartDate();
        LocalDate endDay = dto.getReserveEndDate();

        Period period = Period.between(startDay, endDay);
        int dayDiff = period.getDays();

        int seasonPrice = 0;
        if(seasonType.equals(SeasonType.BEST_PEAK)){
            log.info("극성수기 가격 적용 {}", zone.getBestPeakSeasonPrice());
            seasonPrice = zone.getBestPeakSeasonPrice() * dayDiff;
        } else if(seasonType.equals(SeasonType.PEAK)){
            log.info("성수기 가격 적용 {}", zone.getPeakSeasonPrice());
            seasonPrice = zone.getPeakSeasonPrice() * dayDiff;
        } else {
            log.info("오프 시즌 가격 적용 {}", zone.getOffSeasonPrice());
            seasonPrice = zone.getOffSeasonPrice() * dayDiff;
        }

        if(dto.getAdults() > 2){
            seasonPrice += (dto.getAdults() - 2) * 10000;
        }

        return seasonPrice;
    }

    @Transactional(readOnly = false)
    public void paymentSave(PaymentPostDto paymentPostDto, String json, JwtUserDetails jwtUserDetails) {
        Payment payment = new Payment();
        payment.setPaymentId(paymentPostDto.getPaymentId());
        jsonToPayment(json, "", payment);

        int reservationTotalPrice = calculationTotalPrice(paymentPostDto);
        log.info("reservationTotalPrice (계산 값) : {}", reservationTotalPrice);
        log.info("paymentAmountTotal (클라이언트 값) : {}", payment.getAmountTotal());

        if (reservationTotalPrice != payment.getAmountTotal()) {
            throw new PaymentException(ExceptionCode.PAYMENT_NOT_EQUAL_RESERVATION);
        }

        ReservationPostDto reservationPostDto = getReservationPostDto(paymentPostDto, reservationTotalPrice);
        Reservation reservation = reservationService.createReservationEntity(reservationPostDto);
        payment.setReservation(reservation);

        User user = jwtUserDetails.getUser();
        if (user == null || !payment.getCustomerEmail().equals(user.getEmail())
                && !payment.getCustomerName().equals(user.getName())
                && !payment.getCustomerPhoneNumber().equals(user.getPhoneNumber())) {
            throw new PaymentException(ExceptionCode.USER_INVALID);
        };
        payment.setUser(user);
        paymentRepository.save(payment);
    }

    public void paymentCancel(PaymentCancelPostDto paymentCancelPostDto, String json, JwtUserDetails jwtUserDetails) {
        PaymentCancellation paymentCancellation = jsonToPaymentCancellation(json, paymentCancelPostDto);
        Payment payment = paymentRepository.qFindByPaymentId(paymentCancellation.getPaymentId());
        paymentCancellation.setPayment(payment);

        if (payment.getAmountTotal() != paymentCancellation.getTotalAmount()) {
            throw new PaymentException(ExceptionCode.PAYMENT_NOT_EQUAL_CANCEL);
        }

        paymentCancellationRepository.save(paymentCancellation);
    }

    private PaymentCancellation jsonToPaymentCancellation(String json, PaymentCancelPostDto paymentCancelPostDto) {
        json = new JSONObject(json).getJSONObject("cancellation").toString();
        Gson gson = new Gson();
        PaymentCancellation paymentCancellation = gson.fromJson(json, PaymentCancellation.class);
        paymentCancellation.setPaymentId(paymentCancelPostDto.getPaymentId());
        return paymentCancellation;
    }

    private void jsonToPayment(String json, String keyName, Payment payment) {
        JSONObject obj = new JSONObject(json);

        if (obj.isEmpty()) {
            return;
        } else {
            for (String key : obj.keySet()) {
                if (obj.optJSONObject(key) == null) {
                    if (keyName.equals("")) {
                        String setKeyName = key;
                        payment.setPaymentInstanceVariable(PaymentType.valueOf(setKeyName), String.valueOf(obj.opt(key)));
                    } else {
                        String setKeyName = keyName + key.substring(0, 1).toUpperCase() + key.substring(1);
                        payment.setPaymentInstanceVariable(PaymentType.valueOf(setKeyName), String.valueOf(obj.opt(key)));
                    }

                } else {
                    if (keyName.equals("")) {
                        String setKeyName = key;
                        //payment.setPaymentInstanceVariable(PaymentType.valueOf(setKeyName), obj.optJSONObject(key).toString());
                        jsonToPayment(obj.getJSONObject(key).toString(), key, payment);
                    } else {
                        if (obj.optJSONObject(key) != null) {
                            String setKeyName = keyName + key.substring(0, 1).toUpperCase() + key.substring(1);
                            //payment.setPaymentInstanceVariable(PaymentType.valueOf(setKeyName), obj.optJSONObject(key).toString());
                        } else {
                            String setKeyName = keyName;
                            payment.setPaymentInstanceVariable(PaymentType.valueOf(setKeyName), obj.optJSONObject(key).toString());
                        }
                        jsonToPayment(obj.getJSONObject(key).toString(), keyName + key.substring(0, 1).toUpperCase() + key.substring(1), payment);
                    }
                }
            }
        }
    }
}