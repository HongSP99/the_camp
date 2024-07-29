package io.camp.payment.service;


import io.camp.campsite.model.entity.Campsite;
import io.camp.campsite.repository.CampSiteRepository;
import io.camp.payment.model.Payment;
import io.camp.payment.model.PaymentType;
import io.camp.payment.model.dto.PaymentGetDto;
import io.camp.payment.repository.PaymentRepository;
import io.camp.payment.util.PrintPayment;
import io.camp.reservation.model.Reservation;
import io.camp.reservation.repository.ReservationRepository;
import io.camp.user.model.User;
import io.camp.user.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final AuthService authService;
    private final ReservationRepository reservationRepository;
    private final CampSiteRepository campSiteRepository;

    @Transactional(readOnly = false)
    public void paymentSave(PaymentGetDto paymentGetDto, String json) {
        User user = authService.getVerifiyLoginCurrentUser();

        Payment payment = new Payment();
        payment.setUser(user);

        payment.setPaymentId(paymentGetDto.getPaymentId());
        payment.setCustomerName(user.getName());
        payment.setCustomerEmail(user.getEmail());
        payment.setCustomerPhoneNumber(user.getPhoneNumber());

        paymentRecurSave(json, "", payment);
        paymentRepository.save(payment);
    }

    private void paymentRecurSave(String json, String keyName, Payment payment) {
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
                        paymentRecurSave(obj.getJSONObject(key).toString(), key, payment);
                    } else {
                        if (obj.optJSONObject(key) != null) {
                            String setKeyName = keyName + key.substring(0, 1).toUpperCase() + key.substring(1);
                            payment.setPaymentInstanceVariable(PaymentType.valueOf(setKeyName), obj.optJSONObject(key).toString());
                        } else {
                            String setKeyName = keyName;
                            payment.setPaymentInstanceVariable(PaymentType.valueOf(setKeyName), obj.optJSONObject(key).toString());
                        }
                        paymentRecurSave(obj.getJSONObject(key).toString(), keyName + key.substring(0, 1).toUpperCase() + key.substring(1), payment);
                    }
                }
            }
        }
    }
}
