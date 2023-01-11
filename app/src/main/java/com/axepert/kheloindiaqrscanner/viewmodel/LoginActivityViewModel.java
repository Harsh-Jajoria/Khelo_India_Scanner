package com.axepert.kheloindiaqrscanner.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.axepert.kheloindiaqrscanner.model.response.LoginResponse;
import com.axepert.kheloindiaqrscanner.repository.LoginActivityRepository;

public class LoginActivityViewModel extends ViewModel {
    private LoginActivityRepository loginActivityRepository;

    public LoginActivityViewModel() {
        loginActivityRepository = new LoginActivityRepository();
    }

    public LiveData<LoginResponse> getLoginData(String email, String password) {
        return loginActivityRepository.login(email, password);
    }

}
