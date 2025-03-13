package com.kunnect.KUnnect.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private static final String SECRET_KEY = "your-secret-key-your-secret-keyrewafwvwvvwvwqvvwqfdsfsfsfsafsaf"; // 32 bytes 이상 문자열 사용
    private static final long EXPIRATION_TIME = 86400000; // 1일

    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    // ✅ user_id 포함하여 JWT 생성
    public String generateToken(String email, Long userId, String nickname) {
        return Jwts.builder()
                .setSubject(email) // email을 subject로 설정
                .claim("user_id", userId) // user_id 추가
                .claim("nickname", nickname)  // ✅ nickname 포함
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // ✅ 토큰에서 email 추출
    public String extractEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // ✅ 토큰에서 user_id 추출
    public Long extractUserId(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("user_id", Long.class);
    }

    // ✅ JWT에서 `nickname` 추출
    public String extractNickname(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("nickname", String.class);
    }


    // ✅ 토큰 유효성 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
