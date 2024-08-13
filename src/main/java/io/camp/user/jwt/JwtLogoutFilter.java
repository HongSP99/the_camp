package io.camp.user.jwt;

import io.camp.common.exception.ExceptionCode;
import io.camp.common.exception.user.CustomException;
import io.camp.user.repository.RefreshRepository;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

public class JwtLogoutFilter extends GenericFilterBean {
    private final JwtTokenUtil jwtTokenUtil;
    private final RefreshRepository refreshRepository;

    public JwtLogoutFilter(JwtTokenUtil jwtTokenUtil, RefreshRepository refreshRepository) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.refreshRepository = refreshRepository;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        doFilter((HttpServletRequest) request, (HttpServletResponse) response, chain);
    }

    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String requestUri = request.getRequestURI();
        System.out.println("Request URI: " + requestUri);
        if (!requestUri.matches("^\\/logout$")) {
            filterChain.doFilter(request, response);
            return;
        }

        String requestMethod = request.getMethod();
        System.out.println("Request Method: " + requestMethod);
        if (!requestMethod.equals("POST")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Refresh 토큰 추출
        String refresh = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("refresh")) {
                    refresh = cookie.getValue();
                }
            }
        }

        System.out.println("Refresh Token: " + refresh);
        if (refresh == null) {
            throw new CustomException(ExceptionCode.REFRESH_TOKEN_NOT_FOUND);
        }

        try {
            jwtTokenUtil.isExpired(refresh);
        } catch (ExpiredJwtException e) {
            throw new CustomException(ExceptionCode.REFRESH_TOKEN_EXPIRED);
        }

        String category = jwtTokenUtil.getCategory(refresh);
        System.out.println("Token Category: " + category);
        if (!category.equals("refresh")) {
            throw new CustomException(ExceptionCode.INVALID_REFRESH_TOKEN);
        }

        Boolean isExist = refreshRepository.existsByRefresh(refresh);
        System.out.println("Token Exists in DB: " + isExist);
        if (!isExist) {
            throw new CustomException(ExceptionCode.BAD_REQUEST);
        }

        refreshRepository.deleteByRefresh(refresh);

        // 모든 쿠키 삭제
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookie.setValue("");
                cookie.setMaxAge(0);
                cookie.setPath("/"); // 모든 경로에서 쿠키가 유효하도록 설정
                cookie.setHttpOnly(true);
                cookie.setSecure(false); // HTTPS 사용 시 true로 설정
                response.addCookie(cookie);
            }
        }


    }
}
