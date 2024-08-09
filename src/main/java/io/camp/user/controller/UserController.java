package io.camp.user.controller;

import io.camp.user.jwt.JwtUserDetails;
import io.camp.user.model.User;
import io.camp.user.model.dto.JoinDto;
import io.camp.user.model.dto.LoginDto;
import io.camp.user.model.dto.PasswordDto;
import io.camp.user.model.dto.RoleGetDto;
import io.camp.user.service.UserService;
import io.swagger.v3.oas.annotations.media.Content;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "회원 가입", description = "입력한 정보를 통해 새 사용자를 등록")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 가입 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "409", description = "회원 가입 실패 - 이메일 중복")

    })
    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody JoinDto joinDto) {
        userService.join(joinDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "로그인", description = "자격 증명으로 사용자를 로그인")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "401", description = "권한 없음 - 잘못된 자격 증명"),
            @ApiResponse(responseCode = "500", description = "비밀 번호나 이메일이 틀림")

    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "토큰 로그인 테스트", description = "토큰 기반 로그인이 제대로 작동하는지 테스트")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "토큰 로그인 테스트 성공"),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류")
    })
    @GetMapping("/test")
    public ResponseEntity<?> testTokenLoginUser() {
        userService.testTokenLoginUser();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "사용자 역할 조회", description = "현재 인증된 사용자의 역할을 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "역할 조회 성공"),
            @ApiResponse(responseCode = "404", description = "사용자 없음")
    })
    @GetMapping("/api/role")
    public ResponseEntity<RoleGetDto> getRole(@AuthenticationPrincipal JwtUserDetails jwtUserDetails) {
        RoleGetDto roleGetDto = userService.verifyRole(jwtUserDetails);
        return new ResponseEntity<>(roleGetDto, HttpStatus.OK);
    }

    @Operation(summary = "사용자 프로필 조회", description = " 현재 로그인 중인 사용자의 프로필을 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사용자 프로필 조회 성공"),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음")
    })
    @GetMapping("/api/user/profile")
    public ResponseEntity<User> getUserProfile(@AuthenticationPrincipal JwtUserDetails userDetails) {
        User user = userDetails.getUser();

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(user);
    }

    @Operation(summary = "비밀번호 업데이트", description = "현재 사용자의 비밀번호를 업데이트")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "비밀번호 업데이트 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 - 현재 비밀번호 또는 새 비밀번호가 유효하지 않음")
    })
    @PostMapping("/update-password")
    public ResponseEntity<?> updatePassword(@RequestBody PasswordDto updatePasswordDto) {
        try {
            userService.updatePassword(updatePasswordDto.getCurrentPassword(), updatePasswordDto.getNewPassword());
            return ResponseEntity.ok("비밀번호가 성공적으로 변경되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
