package com.example.security.springsecuritydemo.controller;

import com.example.security.springsecuritydemo.config.TokenProperties;
import com.example.security.springsecuritydemo.model.http.SignInResult;
import com.example.security.springsecuritydemo.model.http.SignInUser;
import com.example.security.springsecuritydemo.service.AuthService;
import com.example.security.springsecuritydemo.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthService authService;
    private TokenProperties tokenProperties;
    private TokenService tokenService;
    private UserDetailsService userDetailsService;

    @Autowired
    public AuthController(AuthService authService, TokenProperties tokenProperties, TokenService tokenService,
                          UserDetailsService userDetailsService) {
        this.authService = authService;
        this.tokenProperties = tokenProperties;
        this.tokenService = tokenService;
        this.userDetailsService = userDetailsService;
    }

    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public SignInResult signIn(@RequestBody SignInUser signInUser) {
        return authService.signIn(signInUser.getUsername(), signInUser.getPassword());
    }
}
