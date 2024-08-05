package io.camp.user.jwt;

import io.camp.common.exception.ExceptionCode;
import io.camp.common.exception.user.CustomException;
import io.camp.user.model.User;
import io.camp.user.model.UserRole;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;

@RequiredArgsConstructor
public class JwtOncePerRequestFilter extends OncePerRequestFilter {
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationToken = request.getHeader("Authorization");

        if (StringUtils.isEmpty(authorizationToken) || authorizationToken.equals("null")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            jwtTokenUtil.isExpired(authorizationToken);
        } catch (ExpiredJwtException e) {
            PrintWriter writer = response.getWriter();
            writer.print("Authorization token expired");
            throw new CustomException(ExceptionCode.AUTHORIZATION_TOKEN_EXPIRED);
        } catch (MalformedJwtException e) {
            PrintWriter writer = response.getWriter();
            writer.print("Authorization token unsupported types");
            throw new CustomException(ExceptionCode.UNSUPPORTED_TOKEN_TYPE);
        }

        String category = jwtTokenUtil.getCategory(authorizationToken);

        if (!category.equals("Authorization")) {
            PrintWriter writer = response.getWriter();
            writer.print("invalid authorization token");
            throw new CustomException(ExceptionCode.INVALID_AUTHORIZATION_TOKEN);
        }

        String email = jwtTokenUtil.getEmail(authorizationToken);
        String password = jwtTokenUtil.getPassword(authorizationToken);
        UserRole role = jwtTokenUtil.getRole(authorizationToken);
        String birthday = jwtTokenUtil.getBirthDay(authorizationToken);
        String phoneNumber = jwtTokenUtil.getPhoneNumber(authorizationToken);
        String gender = jwtTokenUtil.getGender(authorizationToken);
        String name = jwtTokenUtil.getName(authorizationToken);
        Long seq = jwtTokenUtil.getSeq(authorizationToken);


        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);
        user.setBirthday(birthday);
        user.setPhoneNumber(phoneNumber);
        user.setName(name);
        user.setGender(gender);
        user.setSeq(seq);

        JwtUserDetails jwtUserDetails = new JwtUserDetails(user);

        Authentication authToken = new UsernamePasswordAuthenticationToken(jwtUserDetails, null, jwtUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);
        filterChain.doFilter(request, response);
    }
}
