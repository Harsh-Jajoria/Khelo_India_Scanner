package com.axepert.kheloindiaqrscanner.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.axepert.kheloindiaqrscanner.databinding.ActivityLoginBinding;
import com.axepert.kheloindiaqrscanner.model.request.LoginRequest;
import com.axepert.kheloindiaqrscanner.model.response.LoginResponse;
import com.axepert.kheloindiaqrscanner.network.ApiClient;
import com.axepert.kheloindiaqrscanner.network.ApiServices;
import com.axepert.kheloindiaqrscanner.utils.Constants;
import com.axepert.kheloindiaqrscanner.utils.PreferenceManager;
import com.axepert.kheloindiaqrscanner.viewmodel.LoginActivityViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    LoginActivityViewModel viewModel;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferenceManager = new PreferenceManager(this);
        viewModel = new ViewModelProvider(this).get(LoginActivityViewModel.class);
        setListener();
    }

    private void setListener() {
        binding.btnLogin.setOnClickListener(v -> {
            if (isValid()) {
                login(binding.etEmail.getText().toString().trim(), binding.etPassword.getText().toString().trim());
            }
        });
    }

    private void login(String email, String password) {
        isLoading(true);
        try {
            LoginRequest loginRequest = new LoginRequest(email, password);

            ApiClient.getRetrofit().create(ApiServices.class).login(
                    loginRequest
            ).enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        if (response.body().code == 200) {
                            preferenceManager.putString(Constants.KEY_USERNAME, response.body().data.getName());
                            preferenceManager.putString(Constants.KEY_EMAIL, response.body().data.getEmail());
                            preferenceManager.putString(Constants.KEY_PHONE, response.body().data.getPhone());
                            preferenceManager.putString(Constants.KEY_IMAGE, response.body().data.getImage());
                            preferenceManager.putString(Constants.KEY_USER_ID, response.body().data.getId());
                            preferenceManager.putString(Constants.KEY_ACCESS_CODE, response.body().data.getAccess_code());
                            preferenceManager.putString(Constants.KEY_IMAGE_BASE_URL, response.body().data.getBase_url());
                            preferenceManager.putString(Constants.KEY_DEPARTMENT, response.body().data.getDepartment());
                            preferenceManager.putBoolean(Constants.KEY_IS_LOGIN, true);
                            isLoading(false);
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } else {
                            isLoading(false);
                            showToast(response.body().message);
                        }
                    } else {
                        isLoading(false);
                        showToast("Something went wrong!");
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    isLoading(false);
                    showToast("Failed : " + t.getMessage());
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            isLoading(false);
            showToast("Error : " + e.getMessage());
        }

    }

    private void isLoading(Boolean loading) {
        if (loading) {
            binding.btnLogin.setVisibility(View.GONE);
            binding.progress.setVisibility(View.VISIBLE);
        } else {
            binding.btnLogin.setVisibility(View.VISIBLE);
            binding.progress.setVisibility(View.GONE);
        }
    }

    private Boolean isValid() {
        if (binding.etEmail.getText().toString().trim().isEmpty()) {
            showToast("Please enter email address");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.getText().toString().trim()).matches()) {
            showToast("Please enter valid email");
            return false;
        } else if (binding.etPassword.getText().toString().isEmpty()) {
            showToast("Please enter password");
            return false;
        } else {
            return true;
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (preferenceManager.getBoolean(Constants.KEY_IS_LOGIN)) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        }
    }
}