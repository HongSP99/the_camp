package io.camp.user.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.camp.exception.ExceptionCode;
import io.camp.exception.user.CustomException;
import io.camp.user.model.RefreshEntity;
import io.camp.user.model.User;
import io.camp.user.model.UserRole;
import io.camp.user.repository.RefreshRepository;
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
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final RefreshRepository refreshRepository;

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
            throw new CustomException(ExceptionCode.LOGIN_FAILED);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        JwtUserDetails jwtUserDetails = (JwtUserDetails) authentication.getPrincipal();
        String username = jwtUserDetails.getUsername();
        String password = jwtUserDetails.getPassword();
        String role = jwtUserDetails.getRole().getKey();
        String name = jwtUserDetails.getName();
        String birthday = jwtUserDetails.getBirthDay();
        String phoneNumber = jwtUserDetails.getPhoneNumber();
        String gender = jwtUserDetails.getGender();
        Long seq = jwtUserDetails.getSeq();


        //토큰 생성
        String authorization = jwtTokenUtil.createToken("Authorization", username,password,role, name,birthday,phoneNumber,gender,seq, 86400000L);
        String refresh = jwtTokenUtil.createToken("refresh",username,password,role, name,birthday,phoneNumber,gender,seq, 86400000L);

        log.info("Authorization token : " + authorization);
        log.info("refresh token : " + refresh);

        addRefreshEntity(username, refresh,password,name,birthday,phoneNumber,gender,seq,jwtUserDetails.getRole(), 86400000L);

        //응답 설정
        response.setHeader("Authorization", authorization);
        response.addCookie(jwtTokenUtil.createCookie("refresh", refresh));
        response.setStatus(HttpStatus.OK.value());
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws CustomException {
        throw new CustomException(ExceptionCode.INVALID_AUTHORIZATION_TOKEN);
    }

    private void addRefreshEntity(String username, String refresh, String password, String name, String birthday, String phoneNumber, String gender, Long seq,UserRole role, Long expiredMs ) {
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
        refreshEntity.setRole(role); // Role 설정 추가
        refreshEntity.setExpiration(date.toString());


        refreshRepository.save(refreshEntity);
    }
}
