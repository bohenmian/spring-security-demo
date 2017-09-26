package com.example.security.springsecuritydemo.service.impl;

import com.example.security.springsecuritydemo.config.TokenProperties;
import com.example.security.springsecuritydemo.service.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenServiceImpl implements TokenService {

    private static final long serialVersionUID = -3301605591108950415L;

    static final String CLAIM_KEY_USERNAME = "sub";
    static final String CLAIM_KEY_CREATED = "created";

    private TokenProperties tokenProperties;

    @Autowired
    public TokenServiceImpl(TokenProperties tokenProperties) {
        this.tokenProperties = tokenProperties;
    }

    public String getUsernameFromToken(String token) {
        String username = null;
        try {
            Claims claims = this.getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    public Date getCreateDateFromToken(String token) {
        Date createTime;
        try {
            Claims claims = this.getClaimsFromToken(token);
            createTime = (Date) claims.get(CLAIM_KEY_CREATED);
        } catch (Exception e) {
            createTime = null;
        }
        return createTime;
    }


    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(tokenProperties.getSecret())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }
}

