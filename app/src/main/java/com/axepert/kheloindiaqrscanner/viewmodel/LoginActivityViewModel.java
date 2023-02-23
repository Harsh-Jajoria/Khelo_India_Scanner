package com.axepert.kheloindiaqrscanner.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.axepert.kheloindiaqrscanner.model.response.LoginResponse;
import com.axepert.kheloindiaqrscanner.repository.LoginActivityRepository;


public class LoginActivityViewModel extends ViewModel {
    private final LoginActivityRepository loginActivityRepository;

    public LoginActivityViewModel() {
        loginActivityRepository = new LoginActivityRepository();
    }

    public LiveData<LoginResponse> getLoginData(String email, String password, String key, String add) {
        return loginActivityRepository.login(email, password, key, add);
    }

}
