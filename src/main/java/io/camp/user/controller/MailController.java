package io.camp.user.controller;

import io.camp.user.model.email.AuthCodeDto;
import io.camp.user.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;

@Controller
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;


    // 인증 이메일 전송
    @PostMapping("/mailSend")
    public ResponseEntity<HashMap<String, Object>> mailSend(@RequestParam String email) {
        HashMap<String, Object> map = new HashMap<>();

        try {
            int number = mailService.sendMail(email);
            mailService.saveAuthCode(email, number);

            map.put("success", Boolean.TRUE);
            map.put("message", "인증 메일이 발송되었습니다.");

        } catch (Exception e) {
            map.put("success", Boolean.FALSE);
            map.put("error", e.getMessage());
        }

        return ResponseEntity.ok(map);
    }

    @PostMapping("/verify-code")
    public ResponseEntity<HashMap<String, Object>> verifyCode(@RequestBody AuthCodeDto verifyCodeDto) {
        HashMap<String, Object> response = new HashMap<>();

        boolean isVerified = mailService.verifyAuthCode(verifyCodeDto.getEmail(), verifyCodeDto.getAuthNumber());
        if (isVerified) {
            response.put("success", true);
            response.put("message", "인증에 성공했습니다.");
        } else {
            response.put("success", false);
            response.put("message", "인증 번호가 일치하지 않거나 만료되었습니다.");
        }

        return ResponseEntity.ok(response);
    }





}
