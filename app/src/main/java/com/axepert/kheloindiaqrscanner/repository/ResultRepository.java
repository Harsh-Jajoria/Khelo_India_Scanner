package com.axepert.kheloindiaqrscanner.repository;

import com.axepert.kheloindiaqrscanner.network.ApiClient;
import com.axepert.kheloindiaqrscanner.network.ApiServices;

public class ResultRepository {
    public ApiServices apiServices;

    public ResultRepository() {
        apiServices = ApiClient.getRetrofit().create(ApiServices.class);
    }

}
