package io.camp.user.controller;

import io.camp.inventory.model.dto.InventoryDto;
import io.camp.inventory.service.InventoryService;
import io.camp.user.jwt.JwtUserDetails;
import io.camp.user.model.User;
import io.camp.user.model.dto.*;
import io.camp.user.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final InventoryService inventoryService;


    @Operation(summary = "회원 가입", description = "입력한 정보를 통해 새 사용자를 등록")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 가입 성공", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "405", description = "잘못된 메서드 타입", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "409", description = "회원 가입 실패 - 이메일 중복", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "서버 에러", content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody JoinDto joinDto) {
        userService.join(joinDto);
        return ResponseEntity.ok().build();
    }


    @Operation(summary = "로그인", description = "자격 증명(jwt)으로 사용자를 로그인하여 토큰 발급")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "잘못된 비밀번호 입력", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "권한 없음 - 잘못된 자격 증명", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "등록되지 않은 이메일", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "405", description = "잘못된 메서드 타입", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류", content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        return ResponseEntity.ok().build();
    }


    @Operation(summary = "토큰 로그인 테스트", description = "토큰 기반 로그인이 제대로 작동하는지 테스트")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "잘못된 비밀번호 입력", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "권한 없음 - 잘못된 자격 증명", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "등록되지 않은 이메일", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "405", description = "잘못된 메서드 타입", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류", content = @Content(mediaType = "application/json"))
    })
    @Parameters({
            @Parameter(name = "email", description = "로그인 할 이메일 입력", example = "user@example.com", required = true),
            @Parameter(name = "password", description = "해당 이메일의 비밀번호 입력", example = "userPassword", required = true)
    })
    @GetMapping("/test")
    public ResponseEntity<?> testTokenLoginUser() {
        userService.testTokenLoginUser();
        return ResponseEntity.ok().build();
    }


    @Operation(summary = "사용자 역할 조회", description = "헤더에서 jwt 토큰을 받아 사용자 역할(role)를 추출하여 현재 인증된 사용자의 역할을 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "역할 조회 성공", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "유효 하지 않은 사용자", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "사용자 없음", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "405", description = "잘못된 메서드 타입", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류", content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/user/role")
    public ResponseEntity<RoleGetDto> getRole(@AuthenticationPrincipal JwtUserDetails jwtUserDetails) {
        RoleGetDto roleGetDto = userService.verifyRole(jwtUserDetails);
        return ResponseEntity.ok(roleGetDto);
    }


    @Operation(summary = "사용자 데이터 조회", description = "현재 로그인 중인 사용자의 정보를 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사용자 데이터 조회 성공", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "사용자 데이터 없음", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "405", description = "잘못된 메서드 타입", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/user")
    public ResponseEntity<UserDataGetDto> getUserData(@AuthenticationPrincipal JwtUserDetails jwtUserDetails) {
        UserDataGetDto userDataGetDto = userService.getUserData(jwtUserDetails);
        return ResponseEntity.ok(userDataGetDto);
    }


    @Operation(summary = "사용자 프로필 조회", description = "헤더에서 jwt 토큰을 받아 사용자 정보를 추출하여 현재 로그인 중인 사용자의 프로필을 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사용자 프로필 조회 성공", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 사용자", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "405", description = "잘못된 메서드 타입", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "서버 에러", content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/user/profile")
    public ResponseEntity<User> getUserProfile(@AuthenticationPrincipal JwtUserDetails userDetails) {
        User user = userDetails.getUser();
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(user);
    }


    @Operation(summary = "사용자 쿠폰 조회", description = "헤더에서 jwt 토큰을 받아 사용자 seq(id)를 추출하여 사용자가 가지고 있는 쿠폰 리스트를 불러 드림")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "쿠폰 조회 성공", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "유효 하지 않은 사용자", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "쿠폰을 찾을 수 없음", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "405", description = "잘못된 메서드 타입", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "서버 에러", content = @Content(mediaType = "application/json"))

    })
    @GetMapping("/user/inventory")
    public ResponseEntity<List<InventoryDto>> findInventoryByUser(@AuthenticationPrincipal UserDetails userDetails){
        List<InventoryDto> dtos = inventoryService.findInventoriesByUserEmail(userDetails.getUsername());
        return ResponseEntity.ok(dtos);
    }


    @Operation(summary = "비밀번호 업데이트", description = "현재 사용자의 비밀번호를 현재 비밀번호와 대조하여 맞으면 새로운 비밀 번호로 업데이트(8자 이상)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "비밀번호 업데이트 성공", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "현재 비밀번호 또는 새 비밀번호가 유효하지 않음", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "405", description = "잘못된 메서드 타입", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "서버 에러", content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/update-password")
    public ResponseEntity<?> updatePassword(@RequestBody PasswordDto updatePasswordDto) {
        userService.updatePassword(updatePasswordDto.getCurrentPassword(), updatePasswordDto.getNewPassword());
        return ResponseEntity.ok("비밀번호가 성공적으로 변경되었습니다.");
    }


    @Operation(summary = "사용자 예약 조회", description = "현재 사용자의 예약 목록을 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "예약 조회 성공", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "예약을 찾을 수 없음", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "405", description = "잘못된 메서드 타입", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/user/reservation")
    public ResponseEntity<Page<UserReservationDto>> userReservationList(@AuthenticationPrincipal JwtUserDetails jwtUserDetails,
                                                                        @RequestParam(value = "page", defaultValue = "0") int page,
                                                                        @RequestParam(value = "size", defaultValue = "6") int size) {
        return ResponseEntity.ok(userService.userReservationList(page, size, jwtUserDetails));
    }


    @Operation(summary = "회원 탈퇴", description = "현재 사용자의 계정을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 탈퇴 성공", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "서버 에러", content = @Content(mediaType = "application/json"))
    })
    @DeleteMapping("/user/delete")
    public ResponseEntity<?> deleteUserAccount(@AuthenticationPrincipal JwtUserDetails jwtUserDetails) {
        User user = jwtUserDetails.getUser();
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        userService.deleteUserAccount(user.getEmail());
        return ResponseEntity.ok().build();
    }

}
