package com.axepert.kheloindiaqrscanner.network;

import com.axepert.kheloindiaqrscanner.model.request.LoginRequest;
import com.axepert.kheloindiaqrscanner.model.request.ScanRequest;
import com.axepert.kheloindiaqrscanner.model.response.LoginResponse;
import com.axepert.kheloindiaqrscanner.model.response.ScanResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiServices {

    @POST("login2")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("details2")
    Call<ScanResponse> scanResponse(@Body ScanRequest scanRequest);

}
