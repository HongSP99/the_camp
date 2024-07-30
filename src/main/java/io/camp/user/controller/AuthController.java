package io.camp.user.controller;


import io.camp.user.model.User;
import io.camp.user.model.dto.JoinDto;
import io.camp.user.model.dto.LoginDto;
import io.camp.user.model.dto.UserGetDto;
import io.camp.user.service.AuthService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody JoinDto joinDto) {
        authService.join(joinDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/api/user")
    public ResponseEntity<UserGetDto> loginUserData() {
        return new ResponseEntity<>(authService.LoginUserData(), HttpStatus.OK);
    }

    @GetMapping("/api/user/profile")
    public User  profile() {
        return authService.getVerifiyLoginCurrentUser();
    }
}