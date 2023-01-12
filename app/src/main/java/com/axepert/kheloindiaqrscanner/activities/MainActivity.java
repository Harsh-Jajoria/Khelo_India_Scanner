package com.axepert.kheloindiaqrscanner.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.axepert.kheloindiaqrscanner.R;
import com.axepert.kheloindiaqrscanner.databinding.ActivityMainBinding;
import com.axepert.kheloindiaqrscanner.utils.Constants;
import com.axepert.kheloindiaqrscanner.utils.PreferenceManager;
import com.google.mlkit.common.MlKitException;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner;
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private static final String TAG = "MainActivity";
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        preferenceManager = new PreferenceManager(this);
        setProfileValues();
        setListener();
    }

    private void setProfileValues() {
        String imageUrl = String.format(
                "%s/%s",
                preferenceManager.getString(Constants.KEY_IMAGE_BASE_URL),
                preferenceManager.getString(Constants.KEY_IMAGE));

        binding.imgProfile.setAlpha(0f);
        Picasso.get().load(imageUrl).noFade().into(binding.imgProfile, new Callback() {
            @Override
            public void onSuccess() {
                binding.imgProfile.animate().alpha(1f).setDuration(300).start();
            }

            @Override
            public void onError(Exception e) {

            }
        });

        binding.tvName.setText(preferenceManager.getString(Constants.KEY_USERNAME));
        binding.tvDepartment.setText(preferenceManager.getString(Constants.KEY_DEPARTMENT));
    }

    private void setListener() {
        binding.btnScanner.setOnClickListener(v -> startScanner());
        binding.imgLogout.setOnClickListener(v -> {
            preferenceManager.clear();
            startActivity(new Intent(MainActivity.this, LoginActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        });
    }

    private void startScanner() {
        GmsBarcodeScannerOptions.Builder optionsBuilder = new GmsBarcodeScannerOptions.Builder();
        GmsBarcodeScanner gmsBarcodeScanner =
                GmsBarcodeScanning.getClient(this, optionsBuilder.build());
        gmsBarcodeScanner
                .startScan()
                .addOnSuccessListener(barcode -> {
                    Log.d(TAG, "Success: " + getSuccessfulMessage(barcode));
                    Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                    intent.putExtra(Constants.KEY_RESULT, getSuccessfulMessage(barcode));
                    startActivity(intent);
                })
                .addOnFailureListener(e -> {
                    Log.d(TAG, "Failure: " + getErrorMessage(e));
                })
                .addOnCanceledListener(() -> {
                    Log.d(TAG, "Error: " + getString(R.string.error_scanner_cancelled));
                });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    private String getSuccessfulMessage(Barcode barcode) {
        String barcodeValue = barcode.getDisplayValue();
        return barcodeValue;
    }

    private String getErrorMessage(Exception e) {
        if (e instanceof MlKitException) {
            switch (((MlKitException) e).getErrorCode()) {
                case MlKitException.CODE_SCANNER_CAMERA_PERMISSION_NOT_GRANTED:
                    return getString(R.string.error_camera_permission_not_granted);
                case MlKitException.CODE_SCANNER_APP_NAME_UNAVAILABLE:
                    return getString(R.string.error_app_name_unavailable);
                default:
                    return getString(R.string.error_default_message, e);
            }
        } else {
            return e.getMessage();
        }
    }

}