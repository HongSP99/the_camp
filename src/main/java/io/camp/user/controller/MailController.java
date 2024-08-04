package io.camp.user.controller;

import io.camp.exception.ExceptionCode;
import io.camp.exception.user.CustomException;
import io.camp.user.model.email.AuthCodeDto;

import io.camp.user.model.response.MailResponse;
import io.camp.user.service.MailService;
import io.camp.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;
    private final UserService userService;

    @PostMapping("/mailSend")
    public ResponseEntity<MailResponse> mailSend(@RequestParam String email) {
        try {
            int number = mailService.sendMail(email);
            mailService.saveAuthCode(email, number);
            return ResponseEntity.ok(MailResponse.success("인증 메일이 발송되었습니다."));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(MailResponse.error(ExceptionCode.MAIL_SEND_FAILED));
        }
    }

    @PostMapping("/verify-code")
    public ResponseEntity<MailResponse> verifyCode(@RequestBody AuthCodeDto verifyCodeDto) {
        boolean isVerified = mailService.verifyAuthCode(verifyCodeDto.getEmail(), verifyCodeDto.getAuthNumber());

        if (isVerified) {
            return ResponseEntity.ok(MailResponse.success("인증에 성공했습니다."));
        } else {
            return ResponseEntity.ok(MailResponse.error(ExceptionCode.VERIFY_CODE_NOTFOUND));
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<MailResponse> resetPassword(@RequestParam String email) {
        try {
            userService.resetPassword(email);
            return ResponseEntity.ok(MailResponse.success("임시 비밀번호가 이메일로 발송되었습니다."));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(MailResponse.error(ExceptionCode.INTERNAL_SERVER_ERROR));
        }
    }
}
