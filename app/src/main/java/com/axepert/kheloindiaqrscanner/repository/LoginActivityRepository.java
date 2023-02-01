package com.axepert.kheloindiaqrscanner.repository;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.axepert.kheloindiaqrscanner.model.request.LoginRequest;
import com.axepert.kheloindiaqrscanner.model.response.LoginResponse;
import com.axepert.kheloindiaqrscanner.network.ApiClient;
import com.axepert.kheloindiaqrscanner.network.ApiServices;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivityRepository {
    public ApiServices apiServices;

    public LoginActivityRepository() {
        apiServices = ApiClient.getRetrofit().create(ApiServices.class);
    }

    public LiveData<LoginResponse> login(String email, String password) {
        MutableLiveData<LoginResponse> data = new MutableLiveData<>();
        LoginRequest loginRequest = new LoginRequest(email, password);

        apiServices.login(loginRequest).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                data.postValue(null);
            }
        });
        return data;
    }

}
