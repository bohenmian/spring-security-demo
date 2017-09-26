package com.example.security.springsecuritydemo.service.impl;

import com.example.security.springsecuritydemo.enums.HttpResultEnum;
import com.example.security.springsecuritydemo.model.http.SignInResult;
import com.example.security.springsecuritydemo.model.persistence.User;
import com.example.security.springsecuritydemo.repository.UserRepository;
import com.example.security.springsecuritydemo.service.AuthService;
import com.example.security.springsecuritydemo.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    private UserRepository userRepository;
    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;
    private TokenService tokenService;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository, AuthenticationManager authenticationManager,
                           UserDetailsService userDetailsService, TokenService tokenService) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.tokenService = tokenService;
    }

    public SignInResult signIn(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return new SignInResult(HttpResultEnum.USER_NOT_EXIST);
        }
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        String token = tokenService.generateToken(userDetails);
        return new SignInResult(username, token, HttpResultEnum.SIGN_IN_SUCCESS);
    }
}
