package io.camp.payment.service;


import com.google.gson.Gson;
import io.camp.payment.model.Payment;
import io.camp.payment.model.PaymentCancellation;
import io.camp.payment.model.PaymentType;
import io.camp.payment.model.dto.PaymentPostDto;
import io.camp.payment.repository.PaymentCancellationRepository;
import io.camp.payment.repository.PaymentRepository;
import io.camp.reservation.model.Reservation;
import io.camp.reservation.repository.ReservationRepository;
import io.camp.user.model.User;
import io.camp.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentCancellationRepository paymentCancellationRepository;
    private final UserService authService;
    private final ReservationRepository reservationRepository;

    public void paymentCancel(PaymentPostDto paymentPostDto, String json) {
        User user = authService.getVerifiyLoginCurrentUser();

        PaymentCancellation paymentCancellation = jsonToPaymentCancellation(json, paymentPostDto);
        Payment payment = paymentRepository.qFindByPaymentId(paymentCancellation.getPaymentId());
        paymentCancellation.setPayment(payment);

        if (payment.getAmountTotal() != paymentCancellation.getTotalAmount()) {
            throw new RuntimeException("결제 테이블 금액과 취소한 금액이 일치하지 않습니다.");
        }

        paymentCancellationRepository.save(paymentCancellation);
    }

    @Transactional(readOnly = false)
    public void paymentSave(PaymentPostDto paymentPostDto, String json) {
        Payment payment = new Payment();
        payment.setPaymentId(paymentPostDto.getPaymentId());
        jsonToPayment(json, "", payment);

        User user = authService.getVerifiyLoginCurrentUser();
        payment.setUser(user);

//        if (user.getEmail().equals(payment.getCustomerEmail())) {
//            throw new RuntimeException("이메일이 일치하지 않습니다.");
//        } else if (user.getName().equals(payment.getCustomerName())) {
//            throw new RuntimeException(("이름이 일치하지 않습니다."));
//        } else if (user.getPhoneNumber().equals(payment.getCustomerPhoneNumber())) {
//            throw new RuntimeException("휴대폰 번호가 일치하지 않습니다");
//        }

        Reservation findReservation = reservationRepository.findById(paymentPostDto.getReservationId())
                .orElseThrow(() -> new RuntimeException("예약 정보가 존재하지 않습니다."));
        if (findReservation.getTotalPrice() != payment.getAmountTotal()) {
            throw new RuntimeException("예약 테이블에 있는 예약 총금액과 결제금액이 일치하지 않습니다.");
        }
        payment.setReservation(findReservation);

        paymentRepository.save(payment);
    }

    private PaymentCancellation jsonToPaymentCancellation(String json, PaymentPostDto paymentPostDto) {
        json = new JSONObject(json).getJSONObject("cancellation").toString();
        Gson gson = new Gson();
        PaymentCancellation paymentCancellation = gson.fromJson(json, PaymentCancellation.class);
        paymentCancellation.setPaymentId(paymentPostDto.getPaymentId());
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
                        payment.setPaymentInstanceVariable(PaymentType.valueOf(setKeyName), obj.optJSONObject(key).toString());
                        jsonToPayment(obj.getJSONObject(key).toString(), key, payment);
                    } else {
                        if (obj.optJSONObject(key) != null) {
                            String setKeyName = keyName + key.substring(0, 1).toUpperCase() + key.substring(1);
                            payment.setPaymentInstanceVariable(PaymentType.valueOf(setKeyName), obj.optJSONObject(key).toString());
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