//package com.learning.gateway.security;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.security.Keys;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import java.security.Key;
//
//@Component
//public class JwtUtil {
//
//    @Value("${spring.application.jwt.secret}")
//    private String secret;
//
//    private Key getSignKey() {
//        return Keys.hmacShaKeyFor(secret.getBytes());
//    }
//
//    public boolean isValid(String token) {
//        try {
//            extractAllClaims(token);
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
//    }
//
//    public Claims extractAllClaims(S/**/tring token) {
//        return Jwts
//                .parserBuilder()
//                .setSigningKey(getSignKey())
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//    }
//
//    public String extractEmail(String token) {
//        return extractAllClaims(token).getSubject();
//    }
//}
