package io.camp.user.controller;


import io.camp.user.jwt.JwtUserDetails;
import io.camp.user.model.User;
import io.camp.user.model.dto.JoinDto;
import io.camp.user.model.dto.LoginDto;
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
        // Null 체크 및 필요한 경우 예외 처리
        if (user == null) {
            return ResponseEntity.status(404).body(null);
        }
        return ResponseEntity.ok(user);
    }


}