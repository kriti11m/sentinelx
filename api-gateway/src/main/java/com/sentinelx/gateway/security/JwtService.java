package com.sentinelx.gateway.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    public String extractUsername(String token) {

        return Jwts.parser()
                .verifyWith((javax.crypto.SecretKey) getSignKey())
                .build()
                .parseSignedClaims(token)
                .getBody()
                .getSubject();
    }

    public boolean isTokenValid(String token) {

        try {

            extractUsername(token);

            return true;

        } catch (Exception e) {

            return false;
        }
    }

    private Key getSignKey() {

        return Keys.hmacShaKeyFor(secret.getBytes());
    }
}