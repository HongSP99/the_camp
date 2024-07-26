package io.camp.payment.controller;


import io.camp.payment.model.dto.KakaoDto;
import io.camp.user.model.User;
import io.camp.user.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController
@RequiredArgsConstructor
public class KakaoController {

    private final AuthService authService;

    @GetMapping("/test/get")
    public String getKakao() {
        return "get get 해요";
    }

    @PostMapping("/payment/complete")
    public String testPayment(@RequestBody KakaoDto kakaoDto) {
        //User user = authService.getVerifiyLoginCurrentUser();

        try {
            URL url = new URL("https://api.portone.io/payments/" + kakaoDto.getPaymentId());
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "PortOne Hl6qRMtHIBVxLdgUDVGoDy1oGfVDWYzxNeseAaCIhxv6AQgS58vGrp48RP5Z17NfPjM9t21HNrY0wmAw");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            while((line = br.readLine()) != null) {
                System.out.println(line);
                sb.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}