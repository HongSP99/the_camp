package io.camp.payment.service;


import com.google.gson.Gson;
import io.camp.exception.ExceptionCode;
import io.camp.exception.payment.PaymentException;
import io.camp.payment.model.Payment;
import io.camp.payment.model.PaymentCancellation;
import io.camp.payment.model.PaymentType;
import io.camp.payment.model.dto.PaymentPostDto;
import io.camp.payment.repository.PaymentCancellationRepository;
import io.camp.payment.repository.PaymentRepository;
import io.camp.reservation.model.Reservation;
import io.camp.reservation.repository.ReservationRepository;
import io.camp.reservation.service.ReservationService;
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
    private final ReservationService reservationService;

    public void paymentCancel(PaymentPostDto paymentPostDto, String json) {
        PaymentCancellation paymentCancellation = jsonToPaymentCancellation(json, paymentPostDto);
        Payment payment = paymentRepository.qFindByPaymentId(paymentCancellation.getPaymentId());
        paymentCancellation.setPayment(payment);

        if (payment.getAmountTotal() != paymentCancellation.getTotalAmount()) {
            throw new PaymentException(ExceptionCode.PAYMENT_NOT_EQUAL_CANCEL);
        }

        paymentCancellationRepository.save(paymentCancellation);
    }

    @Transactional(readOnly = false)
    public void paymentSave(PaymentPostDto paymentPostDto, String json) {
        Payment payment = new Payment();
        payment.setPaymentId(paymentPostDto.getPaymentId());
        jsonToPayment(json, "", payment);

        Reservation findReservation = reservationService.findReservation(paymentPostDto.getReservationId());

        if (findReservation.getTotalPrice() != payment.getAmountTotal()) {
            throw new PaymentException(ExceptionCode.PAYMENT_NOT_EQUAL_RESERVATION);
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