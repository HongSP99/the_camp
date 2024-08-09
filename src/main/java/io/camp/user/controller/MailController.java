package io.camp.user.controller;

import io.camp.exception.ExceptionCode;
import io.camp.exception.user.VerifyCodeNotFoundException;
import io.camp.user.model.email.AuthCodeDto;
import io.camp.user.model.email.response.MailResponse;
import io.camp.user.service.MailService;
import io.camp.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Controller
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;
    private final UserService userService;

    @Operation(summary = "인증 이메일을 전송", description = "입력한 이메일에 인증 코드를 보냄")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이메일이 성공적으로 보내짐"),
            @ApiResponse(responseCode = "500", description = "서버 에러")
    })
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
    @Operation(summary = "사용자 인증 코드 인증", description = "입력한 이메일로 보낸 인증코드를 인증하는 api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "인증이 완료되었습니다."),
            @ApiResponse(responseCode = "404", description = "해당 이메일의 인증 코드를 확인 할 수 없습니다."),
            @ApiResponse(responseCode = "403", description = "해당 이메일의 인증 코드가 만료되었습니다."),

            @ApiResponse(responseCode = "500", description = "인증 코드가 틀림")

    })
    @PostMapping("/verify-code")
    public ResponseEntity<MailResponse> verifyCode(@RequestBody AuthCodeDto verifyCodeDto) {
        try {
            boolean isVerified = mailService.verifyAuthCode(verifyCodeDto.getEmail(), verifyCodeDto.getAuthNumber());

            if (isVerified) {
                return ResponseEntity.ok(MailResponse.success("인증에 성공했습니다."));
            } else {
                throw new VerifyCodeNotFoundException(ExceptionCode.VERIFY_CODE_NOTFOUND);
            }
        } catch (VerifyCodeNotFoundException e) {
            return ResponseEntity.status(403).body(MailResponse.error(ExceptionCode.VERIFY_CODE_EXPIRED));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(MailResponse.error(ExceptionCode.VERIFY_CODE_NOTFOUND));
        }
    }
    @Operation(summary = "임시 비밀번호 설정", description = "해당 이메일의 임시 비밀번호를 만들어 제공함")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "임시 비밀번호 전송 완료"),
            @ApiResponse(responseCode = "500", description = "임시 비밀번호 전송 실패")
    })
    @PostMapping("/reset-password")
    public ResponseEntity<MailResponse> resetPassword(@RequestParam String email) {
        try {
            userService.resetPassword(email);
            return ResponseEntity.ok(MailResponse.success("임시 비밀번호가 이메일로 발송되었습니다."));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(MailResponse.error(ExceptionCode.MAIL_SEND_FAILED));
        }
    }

}
