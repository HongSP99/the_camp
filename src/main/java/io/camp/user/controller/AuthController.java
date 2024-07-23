package io.camp.user.controller;


import io.camp.user.model.UserRole;
import io.camp.user.model.dto.JoinDto;
import io.camp.user.model.dto.LoginDto;
import io.camp.user.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/test")
    public ResponseEntity<?> testTokenLoginUser() {
        authService.testTokenLoginUser();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
