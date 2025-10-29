package org.example.portfoliospring1.util;

import io.jsonwebtoken.security.Keys;
import org.example.portfoliospring1.domain.dto.UserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

import io.jsonwebtoken.*;

@Component
public class JwtUtil {
    @Value("${jwt.secret}") private String secret;
    private final long expiresMin = 15;

    private Key key() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(Long userId, String nickname, String email) {
        Instant now = Instant.now();

        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .claim("nickname", nickname)
                .claim("email", email)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(Duration.ofMinutes(expiresMin))))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }
}
