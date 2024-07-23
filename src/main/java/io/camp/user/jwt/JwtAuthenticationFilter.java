package io.camp.user.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.camp.user.model.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            User user = objectMapper.readValue(request.getInputStream(), User.class);

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());

            Authentication authenticate = authenticationManager.authenticate(authenticationToken);
            JwtUserDetails jwtUserDetails = (JwtUserDetails) authenticate.getPrincipal();
            System.out.println("로그인 성공 : " + jwtUserDetails.getUsername());
            return authenticate;
        } catch (IOException e) {
            throw new RuntimeException("로그인 실패");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        JwtUserDetails jwtUserDetails = (JwtUserDetails) authentication.getPrincipal();
        String username = jwtUserDetails.getUsername();
        String role = jwtUserDetails.getRole();

        //토큰 생성
        String access = jwtTokenUtil.createToken("access", username, role, 600000L);
        String refresh = jwtTokenUtil.createToken("refresh", username, role, 86400000L);

        log.info("access token : " + access);
        log.info("refresh token : " + refresh);

        //응답 설정
        response.setHeader("access", access);
        response.addCookie(jwtTokenUtil.createCookie("refresh", refresh));
        response.setStatus(HttpStatus.OK.value());
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        response.setStatus(401);
    }
}
