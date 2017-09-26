package com.example.security.springsecuritydemo.service.impl;

import com.example.security.springsecuritydemo.model.persistence.User;
import com.example.security.springsecuritydemo.repository.UserRepository;
import com.example.security.springsecuritydemo.service.AuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private UserRepository userRepository;
    private AuthUserService authUserService;

    @Autowired
    public UserDetailServiceImpl(UserRepository userRepository, AuthUserService authUserService) {
        this.userRepository = userRepository;
        this.authUserService = authUserService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
            return authUserService.createAuthUser(user);
        }
    }
}
