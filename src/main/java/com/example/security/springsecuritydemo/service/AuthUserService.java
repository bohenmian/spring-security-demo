package com.example.security.springsecuritydemo.service;

import com.example.security.springsecuritydemo.model.persistence.User;
import com.example.security.springsecuritydemo.model.service.AuthUser;

public interface AuthUserService {

    AuthUser createAuthUser(User user);
}
