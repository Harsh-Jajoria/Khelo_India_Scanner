package com.axepert.kheloindiaqrscanner.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.axepert.kheloindiaqrscanner.databinding.ActivityLoginBinding;
import com.axepert.kheloindiaqrscanner.utils.Constants;
import com.axepert.kheloindiaqrscanner.utils.PreferenceManager;
import com.axepert.kheloindiaqrscanner.viewmodel.LoginActivityViewModel;

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
        viewModel.getLoginData(email, password).observe(this, response -> {
            if (response.code == 200) {
                preferenceManager.putString(Constants.KEY_USERNAME, response.data.getName());
                preferenceManager.putString(Constants.KEY_EMAIL, response.data.getEmail());
                preferenceManager.putString(Constants.KEY_PHONE, response.data.getPhone());
                preferenceManager.putString(Constants.KEY_IMAGE, response.data.getImage());
                preferenceManager.putString(Constants.KEY_USER_ID, response.data.getId());
                preferenceManager.putString(Constants.KEY_ACCESS_CODE, response.data.getAccess_code());
                preferenceManager.putString(Constants.KEY_IMAGE_BASE_URL, response.data.getBase_url());
                preferenceManager.putString(Constants.KEY_DEPARTMENT, response.data.getDepartment());
                preferenceManager.putBoolean(Constants.KEY_IS_LOGIN, true);
                isLoading(false);
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                isLoading(false);
                Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show();
            }
        });
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
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }
}