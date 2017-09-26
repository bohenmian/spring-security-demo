package com.example.security.springsecuritydemo.service;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

public interface TokenService {

    String getUsernameFromToken(String token);

    Date getCreateDateFromToken(String token);

    Date getExpirationDateFromToken(String token);

    String generateToken(UserDetails userDetails);

    Boolean canTokenBeRefreshed(String token, Date lastPasswordReset);

    String refreshToken(String token);

    Boolean validateToken(String token, UserDetails userDetails);
}
