package com.example.demo.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class TokenProvider {

    private static final String tokenSecret = "AdityaCodes@321";

    public String createAccessToken(SessionUserDetails userDetails) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 10*24*60*60*1000);
        return JWT.create()
                .withSubject(userDetails.getUserId())
                .withIssuedAt(now)
                .withExpiresAt(expiryDate)
                .sign(Algorithm.HMAC256(tokenSecret.getBytes()));
    }

    public String createRefreshToken(SessionUserDetails userDetails) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 365*24*60*60*1000);
        return JWT.create()
                .withSubject(userDetails.getUserId())
                .withIssuedAt(now)
                .withExpiresAt(expiryDate)
                .sign(Algorithm.HMAC256(tokenSecret.getBytes()));
    }

    public String getUsernameFromToken(String token) {
        return JWT.require(Algorithm.HMAC256(tokenSecret.getBytes()))
                .build()
                .verify(token)
                .getSubject();
    }
}
