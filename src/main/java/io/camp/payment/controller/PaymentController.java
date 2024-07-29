package io.camp.payment.controller;


import io.camp.payment.model.dto.PaymentGetDto;
import io.camp.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController
@RequiredArgsConstructor
public class PaymentController {


    private final PaymentService paymentService;

    @PostMapping("/payment/complete")
    public String testPayment(@RequestBody PaymentGetDto paymentGetDto) {
        try {
            URL url = new URL("https://api.portone.io/payments/" + paymentGetDto.getPaymentId());
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "PortOne Hl6qRMtHIBVxLdgUDVGoDy1oGfVDWYzxNeseAaCIhxv6AQgS58vGrp48RP5Z17NfPjM9t21HNrY0wmAw");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder(br.readLine());
            String json = sb.toString();
            paymentService.paymentSave(paymentGetDto, json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}