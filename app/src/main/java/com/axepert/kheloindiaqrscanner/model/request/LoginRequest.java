package com.axepert.kheloindiaqrscanner.model.request;

import androidx.annotation.Keep;

@Keep
public class LoginRequest {
    private String email, password, device_id, ip_address;

    public LoginRequest(String email, String password, String device_id, String ip_address) {
        this.email = email;
        this.password = password;
        this.device_id = device_id;
        this.ip_address = ip_address;
    }
}
