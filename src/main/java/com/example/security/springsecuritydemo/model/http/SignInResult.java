package com.example.security.springsecuritydemo.model.http;

import com.example.security.springsecuritydemo.enums.HttpResultEnum;

public class SignInResult {
    private String username;
    private String token;
    private String description;

    public SignInResult() {
    }

    public SignInResult(String username, String token, HttpResultEnum httpResultEnum) {
        this.username = username;
        this.token = token;
        this.description = httpResultEnum.name();
    }

    public SignInResult(String username, HttpResultEnum httpResultEnum) {
        this.username = username;
        this.description = httpResultEnum.name();
    }

    public SignInResult(HttpResultEnum httpResultEnum) {
        this.description = httpResultEnum.name();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "SignInResult{" +
                "username='" + username + '\'' +
                ", token='" + token + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
