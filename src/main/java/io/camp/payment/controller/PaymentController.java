package io.camp.payment.controller;


import io.camp.payment.model.dto.PaymentCancelPostDto;
import io.camp.payment.model.dto.PaymentPostDto;
import io.camp.payment.service.PaymentService;
import io.camp.user.jwt.JwtUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

@RestController
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @Operation(summary = "결제", description = "클라이언트값과 검증값이 일치하는지 체크하여 결제를 진행함")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "결제에 성공하였습니다."),
            @ApiResponse(responseCode = "400", description = "예약금액과 결제금액이 일치하지 않습니다."),
    })
    @PostMapping("/payment/complete")
    public ResponseEntity savePayment(@RequestBody PaymentPostDto paymentPostDto,
                                      @AuthenticationPrincipal JwtUserDetails jwtUserDetails) throws IOException {
        try {
            URL url = new URL("https://api.portone.io/payments/" + paymentPostDto.getPaymentId());
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "PortOne Hl6qRMtHIBVxLdgUDVGoDy1oGfVDWYzxNeseAaCIhxv6AQgS58vGrp48RP5Z17NfPjM9t21HNrY0wmAw");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder(br.readLine());
            String json = sb.toString();
            paymentService.paymentSave(paymentPostDto, json, jwtUserDetails);
        } catch (Exception e) {
            URL url = new URL("https://api.portone.io/payments/"+ paymentPostDto.getPaymentId() +"/cancel");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "PortOne Hl6qRMtHIBVxLdgUDVGoDy1oGfVDWYzxNeseAaCIhxv6AQgS58vGrp48RP5Z17NfPjM9t21HNrY0wmAw");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            JSONObject requestBody = new JSONObject();
            requestBody.put("reason", "결제 오류");

            try(OutputStream os = conn.getOutputStream()) {
                byte[] input = requestBody.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @Operation(summary = "결제 취소", description = "회원이 결제한 캠핑장을 취소할 수 있습니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "결제 취소에 성공하였습니다."),
            @ApiResponse(responseCode = "400", description = "예약은 하루전부터 취소가 불가능 합니다."),
    })
    @PostMapping("/payment/cancel")
    public ResponseEntity cancelPayment(@RequestBody PaymentCancelPostDto paymentCancelPostDto,
                                        @AuthenticationPrincipal JwtUserDetails jwtUserDetails) {

        paymentService.beforePaymentCancelCheck(paymentCancelPostDto);

        try {
            URL url = new URL("https://api.portone.io/payments/"+ paymentCancelPostDto.getPaymentId() +"/cancel");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "PortOne Hl6qRMtHIBVxLdgUDVGoDy1oGfVDWYzxNeseAaCIhxv6AQgS58vGrp48RP5Z17NfPjM9t21HNrY0wmAw");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            JSONObject requestBody = new JSONObject();
            requestBody.put("reason", paymentCancelPostDto.getReason());

            try(OutputStream os = conn.getOutputStream()) {
                byte[] input = requestBody.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder(br.readLine());
            String json = sb.toString();
            paymentService.paymentCancel(paymentCancelPostDto, json, jwtUserDetails);
        } catch (Exception e) {

            //return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.CREATED);
    }
}