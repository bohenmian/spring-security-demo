package com.example.security.springsecuritydemo.service.impl;

import com.example.security.springsecuritydemo.model.persistence.Authority;
import com.example.security.springsecuritydemo.model.persistence.User;
import com.example.security.springsecuritydemo.model.service.AuthUser;
import com.example.security.springsecuritydemo.service.AuthUserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthUserServiceImpl implements AuthUserService {

    public AuthUser createAuthUser(User user) {
        return new AuthUser(user.getId(), user.getUsername(), user.getPassword(),
                user.getEmail(), mapToGrantedAuthorities(user.getAuthorities()),
                user.getLastPasswordResetDate());
    }

    private List<GrantedAuthority> mapToGrantedAuthorities(List<Authority> authorities) {
        return authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName().name()))
                .collect(Collectors.toList());
    }
}
