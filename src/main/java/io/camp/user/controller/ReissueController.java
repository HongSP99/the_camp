package io.camp.user.controller;

import io.camp.common.exception.ExceptionCode;
import io.camp.common.exception.user.CustomException;
import io.camp.user.jwt.JwtTokenUtil;
import io.camp.user.model.RefreshEntity;
import io.camp.user.model.UserRole;
import io.camp.user.repository.RefreshRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.swagger.v3.oas.annotations.media.Content;
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

import java.io.IOException;
import java.io.PrintWriter;
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
            @ApiResponse(responseCode = "200", description = "토큰이 성공적으로 재발급되었습니다.", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 리프레시 토큰입니다.", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "만료된 리프레시 토큰입니다.", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "리프레시 토큰을 찾을 수 가 없음 ", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "405", description = "잘못된 메서드 요청", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "서버 에러", content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 리프레시 토큰 가져오기
        String refresh = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("refresh")) {
                refresh = cookie.getValue();
            }
        }

        if (refresh == null) {
            PrintWriter writer = response.getWriter();
            writer.print("리프레시 토큰을 찾을 수 없습니다.");
            throw new CustomException(ExceptionCode.REFRESH_TOKEN_NOT_FOUND);
        }

        // 만료 여부 확인
        try {
            jwtTokenUtil.isExpired(refresh);
        }catch (ExpiredJwtException e) {
            PrintWriter writer = response.getWriter();
            writer.print("리프레시 토큰이 만료되었습니다.");
            throw new CustomException(ExceptionCode.REFRESH_TOKEN_EXPIRED);
        } catch (MalformedJwtException e) {
            PrintWriter writer = response.getWriter();
            writer.print("지원하는 토큰 타입이 아닙니다.");
            throw new CustomException(ExceptionCode.UNSUPPORTED_TOKEN_TYPE);
        }




        String category = jwtTokenUtil.getCategory(refresh);
        if (!category.equals("refresh")) {
            PrintWriter writer = response.getWriter();
            writer.print("유효하지 않은 리프레쉬 토큰입니다.");
            throw new CustomException(ExceptionCode.REFRESH_TOKEN_NOT_FOUND);
        }

        Boolean isExist = refreshRepository.existsByRefresh(refresh);
        if (!isExist) {
            PrintWriter writer = response.getWriter();
            writer.print("리프레쉬 토큰을 찾을수 없습니다.");
            throw new CustomException(ExceptionCode.REFRESH_TOKEN_NOT_FOUND);
        }

        String email = jwtTokenUtil.getEmail(refresh);
        UserRole role = jwtTokenUtil.getRole(refresh);
        String name = jwtTokenUtil.getName(refresh);
        String birthday = jwtTokenUtil.getBirthDay(refresh);
        String phoneNumber = jwtTokenUtil.getPhoneNumber(refresh);
        String gender = jwtTokenUtil.getName(refresh);
        Long seq = jwtTokenUtil.getSeq(refresh);

        // 새 JWT 생성
        String newAuthorization = jwtTokenUtil.createToken("Authorization", email,  role.getKey(), name, birthday, phoneNumber, gender, seq, 600000L);
        String newRefresh = jwtTokenUtil.createToken("refresh", email,  role.getKey(), name, birthday, phoneNumber, gender, seq, 86400000L);

        refreshRepository.deleteByRefresh(refresh);
        addRefreshEntity(email, newRefresh, name,  birthday, phoneNumber, gender, seq, 86400000L);

        // 응답 설정
        response.setHeader("Authorization", newAuthorization);
        response.addCookie(createCookie("refresh", newRefresh));
        return new ResponseEntity<>(HttpStatus.OK);
    }



    @Operation(summary = "인증 상태 확인", description = "제공된 리프레시 토큰이 유효하고 만료되지 않았는지 확인합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = " 리프레시 토큰이 유효", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 리프레시 토큰", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = " 만료된 리프레시 토큰입니다.", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = " 리프레시 토큰을 찾을 수 가 없음", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "405", description = " 잘못된 메서드 요청", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = " 서버 에러", content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/auth")
    public ResponseEntity<?> checkAuth(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
            PrintWriter writer = response.getWriter();
            writer.print("리프레시 토큰을 찾을 수 없습니다.");
            throw new CustomException(ExceptionCode.REFRESH_TOKEN_NOT_FOUND);
        }

        try {
            jwtTokenUtil.isExpired(refresh);
        }catch (ExpiredJwtException e) {
            PrintWriter writer = response.getWriter();
            writer.print("리프레시 토큰이 만료되었습니다.");
            throw new CustomException(ExceptionCode.REFRESH_TOKEN_EXPIRED);
        } catch (MalformedJwtException e) {
            PrintWriter writer = response.getWriter();
            writer.print("지원하는 토큰 타입이 아닙니다.");
            throw new CustomException(ExceptionCode.UNSUPPORTED_TOKEN_TYPE);
        }




        String category = jwtTokenUtil.getCategory(refresh);
        if (!category.equals("refresh")) {
            PrintWriter writer = response.getWriter();
            writer.print("유효하지 않은 리프레쉬 토큰입니다.");
            throw new CustomException(ExceptionCode.REFRESH_TOKEN_NOT_FOUND);
        }

        Boolean isExist = refreshRepository.existsByRefresh(refresh);
        if (!isExist) {
            PrintWriter writer = response.getWriter();
            writer.print("리프레쉬 토큰을 찾을수 없습니다.");
            throw new CustomException(ExceptionCode.REFRESH_TOKEN_NOT_FOUND);
        }

        return ResponseEntity.ok().body("인증되었습니다.");
    }

    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24 * 60 * 60); // 1일
        cookie.setHttpOnly(true);
        return cookie;
    }

    private void addRefreshEntity(String username, String refresh,  String name, String birthday, String phoneNumber, String gender, Long seq, Long expiredMs) {
        Date date = new Date(System.currentTimeMillis() + expiredMs);

        RefreshEntity refreshEntity = new RefreshEntity();
        refreshEntity.setUsername(username);
        refreshEntity.setRefresh(refresh);
        refreshEntity.setName(name);
        refreshEntity.setBirthday(birthday);
        refreshEntity.setPhoneNumber(phoneNumber);
        refreshEntity.setGender(gender);
        refreshEntity.setSeq(seq);
        refreshEntity.setExpiration(date.toString());

        refreshRepository.save(refreshEntity);
    }
}
