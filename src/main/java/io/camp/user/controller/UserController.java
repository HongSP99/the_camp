package io.camp.user.controller;


import io.camp.user.jwt.JwtUserDetails;
import io.camp.user.model.User;
import io.camp.user.model.dto.JoinDto;
import io.camp.user.model.dto.LoginDto;
import io.camp.user.model.dto.PasswordDto;
import io.camp.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody JoinDto joinDto) {
        userService.join(joinDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/test")
    public ResponseEntity<?> testTokenLoginUser() {
        userService.testTokenLoginUser();
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/api/user/profile")
    public ResponseEntity<User> getUserProfile(@AuthenticationPrincipal JwtUserDetails userDetails) {
        User user = userDetails.getUser();

        if (user == null) {
            return ResponseEntity.status(404).body(null);
        }
        return ResponseEntity.ok(user);
    }

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