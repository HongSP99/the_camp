package io.camp.user.controller;

import io.camp.user.jwt.JwtTokenUtil;
import io.camp.user.model.RefreshEntity;
import io.camp.user.model.UserRole;
import io.camp.user.repository.RefreshRepository;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.Date;

@RestController
public class ReissueController {

    private final JwtTokenUtil jwtTokenUtil;
    private final RefreshRepository refreshRepository;

    public ReissueController(JwtTokenUtil jwtTokenUtil, RefreshRepository refreshRepository) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.refreshRepository = refreshRepository;
    }

    @Operation(summary = "JWT 토큰 재발급", description = "제공된 리프레시 토큰이 유효한 경우 JWT 접근 토큰과 리프레시 토큰을 재발급합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "토큰이 성공적으로 재발급되었습니다."),
            @ApiResponse(responseCode = "400", description = "유효하지 않거나 만료된 리프레시 토큰입니다."),
    })
    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {
        // 리프레시 토큰 가져오기
        String refresh = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("refresh")) {
                refresh = cookie.getValue();
            }
        }

        if (refresh == null) {
            return new ResponseEntity<>("리프레시 토큰이 없습니다", HttpStatus.BAD_REQUEST);
        }

        // 만료 여부 확인
        try {
            jwtTokenUtil.isExpired(refresh);
        } catch (ExpiredJwtException e) {
            return new ResponseEntity<>("리프레시 토큰이 만료되었습니다", HttpStatus.BAD_REQUEST);
        }

        // 토큰 카테고리 확인
        String category = jwtTokenUtil.getCategory(refresh);
        if (!category.equals("refresh")) {
            return new ResponseEntity<>("유효하지 않은 리프레시 토큰입니다", HttpStatus.BAD_REQUEST);
        }

        Boolean isExist = refreshRepository.existsByRefresh(refresh);
        if (!isExist) {
            return new ResponseEntity<>("유효하지 않은 리프레시 토큰입니다", HttpStatus.BAD_REQUEST);
        }

        String email = jwtTokenUtil.getEmail(refresh);
        String password = jwtTokenUtil.getPassword(refresh);
        UserRole role = jwtTokenUtil.getRole(refresh);
        String name = jwtTokenUtil.getName(refresh);
        String birthday = jwtTokenUtil.getBirthDay(refresh);
        String phoneNumber = jwtTokenUtil.getPhoneNumber(refresh);
        String gender = jwtTokenUtil.getName(refresh);
        Long seq = jwtTokenUtil.getSeq(refresh);

        // 새 JWT 생성
        String newAuthorization = jwtTokenUtil.createToken("Authorization", email, password, role.getKey(), name, birthday, phoneNumber, gender, seq, 600000L);
        String newRefresh = jwtTokenUtil.createToken("refresh", email, password, role.getKey(), name, birthday, phoneNumber, gender, seq, 86400000L);

        refreshRepository.deleteByRefresh(refresh);
        addRefreshEntity(email, newRefresh, name, password, birthday, phoneNumber, gender, seq, 86400000L);

        // 응답 설정
        response.setHeader("Authorization", newAuthorization);
        response.addCookie(createCookie("refresh", newRefresh));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "인증 상태 확인", description = "제공된 리프레시 토큰이 유효하고 만료되지 않았는지 확인합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "토큰이 유효합니다."),
            @ApiResponse(responseCode = "401", description = "유효하지 않거나 만료된 리프레시 토큰입니다."),
    })
    @GetMapping("/api/auth")
    public ResponseEntity<?> checkAuth(HttpServletRequest request) {
        String refresh = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("refresh")) {
                    refresh = cookie.getValue();
                }
            }
        }

        if (refresh == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("리프레시 토큰이 없습니다.");
        }

        try {
            jwtTokenUtil.isExpired(refresh);
        } catch (ExpiredJwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("리프레시 토큰이 만료되었습니다.");
        }

        String category = jwtTokenUtil.getCategory(refresh);
        if (!category.equals("refresh")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 리프레시 토큰입니다.");
        }

        Boolean isExist = refreshRepository.existsByRefresh(refresh);
        if (!isExist) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 리프레시 토큰입니다.");
        }

        return ResponseEntity.ok().body("인증되었습니다.");
    }

    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24 * 60 * 60); // 1일
        cookie.setHttpOnly(true);
        return cookie;
    }

    private void addRefreshEntity(String username, String refresh, String password, String name, String birthday, String phoneNumber, String gender, Long seq, Long expiredMs) {
        Date date = new Date(System.currentTimeMillis() + expiredMs);

        RefreshEntity refreshEntity = new RefreshEntity();
        refreshEntity.setUsername(username);
        refreshEntity.setRefresh(refresh);
        refreshEntity.setPassword(password);
        refreshEntity.setName(name);
        refreshEntity.setBirthday(birthday);
        refreshEntity.setPhoneNumber(phoneNumber);
        refreshEntity.setGender(gender);
        refreshEntity.setSeq(seq);
        refreshEntity.setExpiration(date.toString());

        refreshRepository.save(refreshEntity);
    }
}
