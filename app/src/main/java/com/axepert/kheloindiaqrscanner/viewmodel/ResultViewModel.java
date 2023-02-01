package com.axepert.kheloindiaqrscanner.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.axepert.kheloindiaqrscanner.model.response.ScanResponse;
import com.axepert.kheloindiaqrscanner.repository.ResultRepository;


public class ResultViewModel extends ViewModel {
    private final ResultRepository resultRepository;

    public ResultViewModel() {
        resultRepository = new ResultRepository();
    }

    public LiveData<ScanResponse> scan(String userId, String accessCode, String name, String image) {
        return resultRepository.scan(userId, accessCode, name, image);
    }

}
