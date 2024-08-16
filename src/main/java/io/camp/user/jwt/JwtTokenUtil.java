    package io.camp.user.jwt;

    import io.camp.user.model.UserRole;
    import io.jsonwebtoken.Jwts;
    import jakarta.servlet.http.Cookie;
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.stereotype.Component;

    import javax.crypto.SecretKey;
    import javax.crypto.spec.SecretKeySpec;
    import java.nio.charset.StandardCharsets;
    import java.util.Date;

    @Component
    public class JwtTokenUtil {
        private SecretKey secretKey;

        public JwtTokenUtil(@Value("${spring.jwt.secret}") String secret) {
            secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
        }

        public String getCategory(String token) {
            return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("category", String.class);
        }

        public String getEmail(String token) {
            return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("email", String.class);
        }
        public Long getSeq(String token) {
            return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("seq", Long.class);
        }

        public String getPhoneNumber(String token) {
            return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("phoneNumber", String.class);
        }
        public String getGender(String token) {
            return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("gender", String.class);
        }
        public String getName(String token) {
            return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("name", String.class);
        }
        public String getBirthDay(String token) {
            return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("birthday", String.class);
        }

        public String getPassword(String token) {
            return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("password", String.class);
        }

        public UserRole getRole(String token) {
            String role = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
            return UserRole.valueOf(role.substring(5));
        }

        public Boolean isExpired(String token) {
            return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
        }

        public String createToken(String category, String email, String role, String name, String birthday, String phoneNumber, String gender, Long seq, Long expiredMs) {
            return Jwts.builder()
                    .claim("category", category)
                    .claim("email", email)
                    .claim("role", role)
                    .claim("name", name)
                    .claim("birthday", birthday)
                    .claim("phoneNumber", phoneNumber)
                    .claim("gender", gender)
                    .claim("seq", seq)
                    .issuedAt(new Date())
                    .expiration(new Date(System.currentTimeMillis() + expiredMs))
                    .signWith(secretKey)
                    .compact();
        }


        public Cookie createCookie(String key, String value) {
            Cookie cookie = new Cookie(key, value);
            cookie.setMaxAge(24*60*60);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            return cookie;
        }
    }
