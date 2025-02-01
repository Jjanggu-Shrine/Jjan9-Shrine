    package com.example.jjangushrine.config.security.jwt;

    import com.example.jjangushrine.config.security.SecurityConst;
    import com.example.jjangushrine.config.security.entity.CustomUserDetails;
    import io.jsonwebtoken.*;
    import io.jsonwebtoken.security.Keys;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.security.core.Authentication;
    import org.springframework.security.core.GrantedAuthority;
    import org.springframework.stereotype.Component;

    import javax.crypto.SecretKey;
    import java.util.Base64;
    import java.util.Date;
    import java.util.stream.Collectors;

    @Slf4j
    @Component
    public class JwtUtil {

        private final SecretKey secretKey;

        public JwtUtil(@Value("${jwt.secret.key}") String secret) {
            byte[] keyBytes = Base64.getDecoder().decode(secret);
            this.secretKey = Keys.hmacShaKeyFor(keyBytes);
        }

        public String generateToken(Authentication authentication) {
            CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
            Date now = new Date();

            return SecurityConst.BEARER_PREFIX + Jwts.builder()
                    .claim("id", principal.getId())
                    .claim("email", principal.getUsername())
                    .claim("role", getAuthorities(authentication))
                    .issuedAt(now)
                    .expiration(new Date(now.getTime() + SecurityConst.TOKEN_TIME))
                    .signWith(secretKey)
                    .compact();
        }

        public Claims extractClaims(String token) {
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        }

        private String getAuthorities(Authentication authentication) {
            return authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(","));
        }
    }

