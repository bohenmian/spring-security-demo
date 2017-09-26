package com.example.security.springsecuritydemo.service.impl;

import com.example.security.springsecuritydemo.config.TokenProperties;
import com.example.security.springsecuritydemo.model.service.AuthUser;
import com.example.security.springsecuritydemo.service.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

    public Date getExpirationDateFromToken(String token) {
        Date expirationTime;
        try {
            Claims claims = this.getClaimsFromToken(token);
            expirationTime = claims.getExpiration();
        } catch (Exception e) {
            expirationTime = null;
        }
        return expirationTime;
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

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + tokenProperties.getExpiration() * 1000);
    }

    private Boolean isTokenExpired(String token) {
        Date expiration = this.getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());
        return this.generateToken(claims);
    }

    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, tokenProperties.getSecret())
                .compact();
    }

    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        Date createTime = this.getCreateDateFromToken(token);
        return !isCreatedBeforeLastPasswordReset(createTime, lastPasswordReset) && !isTokenExpired(token);
    }

    public String refreshToken(String token) {
        String refreshToken;
        try {
            Claims claims = this.getClaimsFromToken(token);
            claims.put(CLAIM_KEY_CREATED, new Date());
            refreshToken = this.generateToken(claims);
        } catch (Exception e) {
            refreshToken = null;
        }
        return refreshToken;
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        AuthUser user = (AuthUser) userDetails;
        String username = this.getUsernameFromToken(token);
        Date createTime = this.getCreateDateFromToken(token);
        return username.equals(user.getUsername()) && isTokenExpired(token) && isCreatedBeforeLastPasswordReset(createTime, user.getLastPasswordResetDate());
    }

}

