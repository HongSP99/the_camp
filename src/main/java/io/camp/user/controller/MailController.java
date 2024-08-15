package io.camp.user.controller;

import io.camp.common.exception.ExceptionCode;
import io.camp.common.exception.user.MailSendFailedException;
import io.camp.common.exception.user.VerifyCodeNotFoundException;
import io.camp.user.model.email.response.MailResponse;
import io.camp.user.model.email.AuthCodeDto;
import io.camp.user.service.MailService;
import io.camp.user.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import jakarta.mail.MessagingException;
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
            @ApiResponse(responseCode = "200", description = "이메일이 성공적으로 보내짐", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "405", description = "잘못된 메서드 타입", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "서버 에러", content = @Content(mediaType = "application/json"))
    })
    @Parameters({
            @Parameter(name = "email", description = "인증 코드를 전송할 이메일 주소", example = "user@example.com", required = true)
    })
    @PostMapping("/mailSend")
    public ResponseEntity<MailResponse> mailSend(@RequestParam("email") String email) {
        int number = mailService.sendMail(email);
        mailService.saveAuthCode(email, number);
        return ResponseEntity.ok(MailResponse.success("인증 메일이 발송되었습니다."));
    }

    @Operation(summary = "사용자 인증 코드 인증", description = "입력한 이메일로 보낸 인증코드를 인증하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "인증이 완료되었습니다.", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "해당 이메일의 인증 코드가 만료되었습니다.", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "해당 이메일의 인증 코드를 확인 할 수 없습니다.", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "405", description = "잘못된 메서드 타입", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "인증 코드가 틀림", content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/verify-code")
    public ResponseEntity<MailResponse> verifyCode(@RequestBody AuthCodeDto verifyCodeDto) {
        mailService.verifyAuthCode(verifyCodeDto.getEmail(), verifyCodeDto.getAuthNumber());
        return ResponseEntity.ok(MailResponse.success("인증에 성공했습니다."));
    }

    @Operation(summary = "임시 비밀번호 설정", description = "해당 이메일의 임시 비밀번호를 만들어 제공함")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "임시 비밀번호 전송 완료", content = @Content(mediaType = "application/x-www-form-urlencoded")),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(mediaType = "application/x-www-form-urlencoded")),
            @ApiResponse(responseCode = "404", description = "등록되지 않은 이메일", content = @Content(mediaType = "application/x-www-form-urlencoded")),
            @ApiResponse(responseCode = "405", description = "잘못된 메서드 타입", content = @Content(mediaType = "application/x-www-form-urlencoded")),
            @ApiResponse(responseCode = "500", description = "임시 비밀번호 전송 실패", content = @Content(mediaType = "application/x-www-form-urlencoded"))
    })
    @Parameters({
            @Parameter(name = "email", description = "임시 비밀번호를 전송할 이메일 주소", required = true)
    })
    @PostMapping("/reset-password")
    public ResponseEntity<MailResponse> resetPassword(@RequestParam("email") String email) throws MessagingException {
        userService.resetPassword(email);
        return ResponseEntity.ok(MailResponse.success("임시 비밀번호가 이메일로 발송되었습니다."));
    }
}
