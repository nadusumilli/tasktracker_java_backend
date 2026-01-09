package com.tasktracker.tasktracker.service;

import com.tasktracker.tasktracker.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JWTService {

    @Value("${jwt.secretKey}")
    private String jwtSecretKey;

    public SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessTokenFromUser(User user) {
        return Jwts
                .builder()
                .subject(user.getId())
                .claim("username", user.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime()+ 1000 * 60 * 10))
                .signWith(getSecretKey())
                .compact();
    }

    public String generateRefreshTokenFromUser(User user) {
        return Jwts
                .builder()
                .subject(user.getId())
                .claim("username", user.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime()+ 1000L * 60 * 60 * 24 * 30))
                .signWith(getSecretKey())
                .compact();
    }

    public String getUserIdFromToken(String token) {
        return Jwts
                .parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}
