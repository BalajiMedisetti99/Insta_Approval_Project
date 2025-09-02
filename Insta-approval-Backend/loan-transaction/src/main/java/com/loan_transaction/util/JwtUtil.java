package com.loan_transaction.util;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

@Component
public class JwtUtil {

    private final SecretKey secretKey;

    public JwtUtil() {
        
        secretKey = Keys.hmacShaKeyFor("My$uper$ecretKey1656!My$uper$ecretKey1656!".getBytes(StandardCharsets.UTF_8));
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