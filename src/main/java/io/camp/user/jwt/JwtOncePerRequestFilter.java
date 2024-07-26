package io.camp.user.jwt;

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
        String accessToken = request.getHeader("access");

        if (StringUtils.isEmpty(accessToken) || accessToken.equals("null")) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            jwtTokenUtil.isExpired(accessToken);
        } catch (ExpiredJwtException e) {
            PrintWriter writer = response.getWriter();
            writer.print("access token expired");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        } catch (MalformedJwtException e) {
            PrintWriter writer = response.getWriter();
            writer.print("access token unsupported types");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }


        String category = jwtTokenUtil.getCategory(accessToken);

        if (!category.equals("access")) {
            PrintWriter writer = response.getWriter();
            writer.print("invalid access token");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String email = jwtTokenUtil.getEmail(accessToken);
        UserRole role = jwtTokenUtil.getRole(accessToken);

        User user = new User();
        user.setEmail(email);
        user.setRole(role);
        JwtUserDetails jwtUserDetails = new JwtUserDetails(user);

        Authentication authToken = new UsernamePasswordAuthenticationToken(jwtUserDetails, null, jwtUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);
        filterChain.doFilter(request, response);
    }
}
