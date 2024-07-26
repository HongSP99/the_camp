package io.camp.payment.service;


import io.camp.payment.model.Payment;
import io.camp.payment.model.dto.KakaoDto;
import io.camp.payment.repository.PaymentRepository;
import io.camp.payment.util.PrintPayment;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;

    @Transactional(readOnly = false)
    public void paymentSave(KakaoDto kakaoDto, String json) {
        Payment payment = new Payment();
        payment.setPaymentId(kakaoDto.getPaymentId());
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
                        payment.setPaymentInstanceVariable(setKeyName, String.valueOf(obj.opt(key)));
                    } else {
                        String setKeyName = keyName + key.substring(0, 1).toUpperCase() + key.substring(1);
                        payment.setPaymentInstanceVariable(setKeyName, String.valueOf(obj.opt(key)));
                    }

                } else {
                    if (keyName.equals("")) {
                        String setKeyName = key;
                        payment.setPaymentInstanceVariable(setKeyName, obj.optJSONObject(key).toString());
                        paymentRecurSave(obj.getJSONObject(key).toString(), key, payment);
                    } else {
                        if (obj.optJSONObject(key) != null) {
                            String setKeyName = keyName + key.substring(0, 1).toUpperCase() + key.substring(1);
                            payment.setPaymentInstanceVariable(setKeyName, obj.optJSONObject(key).toString());
                        } else {
                            String setKeyName = keyName;
                            payment.setPaymentInstanceVariable(setKeyName, obj.optJSONObject(key).toString());
                        }
                        paymentRecurSave(obj.getJSONObject(key).toString(), keyName + key.substring(0, 1).toUpperCase() + key.substring(1), payment);
                    }
                }
            }
        }
    }
}
