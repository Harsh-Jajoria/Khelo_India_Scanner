package com.axepert.kheloindiaqrscanner.model.request;

public class LoginRequest {
    private String email, password;

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}