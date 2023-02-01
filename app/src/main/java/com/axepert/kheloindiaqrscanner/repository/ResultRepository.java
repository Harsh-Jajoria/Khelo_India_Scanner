package com.axepert.kheloindiaqrscanner.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.axepert.kheloindiaqrscanner.model.request.ScanRequest;
import com.axepert.kheloindiaqrscanner.model.response.ScanResponse;
import com.axepert.kheloindiaqrscanner.network.ApiClient;
import com.axepert.kheloindiaqrscanner.network.ApiServices;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultRepository {
    public ApiServices apiServices;

    public ResultRepository() {
        apiServices = ApiClient.getRetrofit().create(ApiServices.class);
    }

    public LiveData<ScanResponse> scan(String userId, String accessCode, String name, String image) {
        MutableLiveData<ScanResponse> data = new MutableLiveData<>();
        ScanRequest scanRequest = new ScanRequest(
                userId,
                accessCode,
                name,
                image
        );

        apiServices.scanResponse(scanRequest).enqueue(new Callback<ScanResponse>() {
            @Override
            public void onResponse(@NonNull Call<ScanResponse> call, @NonNull Response<ScanResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ScanResponse> call, @NonNull Throwable t) {
                data.postValue(null);
            }
        });
        return data;
    }

}
