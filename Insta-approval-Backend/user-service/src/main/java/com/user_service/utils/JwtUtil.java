package com.user_service.utils;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.user_service.model.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

@Component
public class JwtUtil {

    private final SecretKey secretKey;

    public JwtUtil() {
        // Must be at least 32 chars
        secretKey = Keys.hmacShaKeyFor("My$uper$ecretKey1656!My$uper$ecretKey1656!".getBytes(StandardCharsets.UTF_8));
    }

    // ✅ New method: generate token with userId + email + roles
    public String generateToken(Long userId, String email, Set<Role> roles) {
        return Jwts.builder()
                .setSubject(email)
                .claim("userId", userId)
                .claim("roles", roles.stream().map(Role::getName).toList())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day expiry
                .signWith(secretKey)
                .compact();
    }

    public Long extractUserId(String token) {
        Claims claims = getClaims(token);
        Object userIdObj = claims.get("userId");

        if (userIdObj instanceof Number) {
            return ((Number) userIdObj).longValue();
        } else if (userIdObj instanceof String) {
            return Long.parseLong((String) userIdObj);
        }
        return null;
    }

    public boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                   .setSigningKey(secretKey)
                   .build()
                   .parseClaimsJws(token)
                   .getBody();
    }
}
