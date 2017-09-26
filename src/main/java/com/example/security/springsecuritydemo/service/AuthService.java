package com.example.security.springsecuritydemo.service;

import com.example.security.springsecuritydemo.model.http.SignInResult;

public interface AuthService {

    SignInResult signIn(String username, String password);
}
