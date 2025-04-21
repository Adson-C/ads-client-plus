package com.ads.clien.plus.service.impl;

import com.ads.clien.plus.service.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenServiceImpl implements TokenService {

    @Value("${webservices.ads.jwt.expiration}")
    private String expiration;
    @Value("${webservices.ads.jwt.secret}")
    private String secret;

    @Override
    public String getToken(Long userId) {
        Date tody = new Date();
        Date expirationDate = new Date(tody.getTime() + Long.parseLong(expiration));
        return Jwts.builder()
                .setIssuer("ads-client-plus")
                .setSubject(userId.toString())
                .setIssuedAt(tody)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    @Override
    public Boolean isValid(String token) {
        try {
            getClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Long getUserId(String token) {
        Jws<Claims> claims = getClaimsJws(token);
        return Long.parseLong(claims.getBody().getSubject());
    }

    private Jws<Claims> getClaimsJws(String token) {
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
    }
}
