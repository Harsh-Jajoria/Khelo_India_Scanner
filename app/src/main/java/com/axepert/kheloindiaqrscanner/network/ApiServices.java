package com.axepert.kheloindiaqrscanner.network;

import com.axepert.kheloindiaqrscanner.model.request.LoginRequest;
import com.axepert.kheloindiaqrscanner.model.response.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiServices {

    @POST("login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

}
