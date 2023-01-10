package com.axepert.kheloindiaqrscanner;

import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.axepert.kheloindiaqrscanner.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.mlkit.common.MlKitException;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner;
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setListener();
        startScanner();
    }

    private void setListener() {
        binding.btnScanner.setOnClickListener(v -> startScanner());
    }

    private void startScanner() {
        GmsBarcodeScannerOptions.Builder optionsBuilder = new GmsBarcodeScannerOptions.Builder();
        GmsBarcodeScanner gmsBarcodeScanner =
                GmsBarcodeScanning.getClient(this, optionsBuilder.build());
        gmsBarcodeScanner
                .startScan()
                .addOnSuccessListener(barcode -> {
                    Log.d(TAG, "Success: " + getSuccessfulMessage(barcode));
                    successSnackBar(getSuccessfulMessage(barcode));
                })
                .addOnFailureListener(e -> {
                    Log.d(TAG, "Failure: " + getErrorMessage(e));
                    errorSnackBar(getErrorMessage(e));
                })
                .addOnCanceledListener(() -> {
                    Log.d(TAG, "Error: " + getString(R.string.error_scanner_cancelled));
                    errorSnackBar(getString(R.string.error_scanner_cancelled));
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
        String barcodeValue =
                String.format(
                        Locale.getDefault(),
                        "Display Value: %s\nRaw Value: %s\nFormat: %s\nValue Type: %s",
                        barcode.getDisplayValue(),
                        barcode.getRawValue(),
                        barcode.getFormat(),
                        barcode.getValueType());
        return getString(R.string.barcode_result, barcodeValue);
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

    private void warningSnackBar(String msg) {
        Snackbar snackbar = Snackbar.make(binding.getRoot(), "", 5000);
        View view = LayoutInflater.from(this).inflate(R.layout.warning_layout, findViewById(R.id.snackBarRootLayout));
        snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
        Snackbar.SnackbarLayout snackBarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        snackBarLayout.setPadding(0, 0, 0, 0);

        TextView title = view.findViewById(R.id.tvTitle);
        TextView message = view.findViewById(R.id.tvMessage);
        ImageView imgClose = view.findViewById(R.id.imgClose);

        imgClose.setOnClickListener(v -> snackbar.dismiss());

        message.setText(msg);

        snackBarLayout.addView(view);
        snackbar.setAnchorView(binding.btnScanner);
        snackbar.show();
    }

    private void errorSnackBar(String error) {
        Snackbar snackbar = Snackbar.make(binding.getRoot(), error, 5000);
        View view = LayoutInflater.from(this).inflate(R.layout.error_layout, findViewById(R.id.snackBarRootLayout));
        snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
        Snackbar.SnackbarLayout snackBarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        snackBarLayout.setPadding(0, 0, 0, 0);

        TextView title = view.findViewById(R.id.tvTitle);
        TextView message = view.findViewById(R.id.tvMessage);
        ImageView imgClose = view.findViewById(R.id.imgClose);

        imgClose.setOnClickListener(v -> snackbar.dismiss());

        message.setText(error);

        snackBarLayout.addView(view);
        snackbar.setAnchorView(binding.btnScanner);
        snackbar.show();
    }

    private void successSnackBar(String msg) {
        Snackbar snackbar = Snackbar.make(binding.getRoot(), "", Snackbar.LENGTH_INDEFINITE);
        View view = LayoutInflater.from(this).inflate(R.layout.success_layout, findViewById(R.id.snackBarRootLayout));
        snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
        Snackbar.SnackbarLayout snackBarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        snackBarLayout.setPadding(0, 0, 0, 0);

        TextView title = view.findViewById(R.id.tvTitle);
        TextView message = view.findViewById(R.id.tvMessage);
        ImageView imgClose = view.findViewById(R.id.imgClose);

        imgClose.setOnClickListener(v -> snackbar.dismiss());

        message.setText(msg);

        snackBarLayout.addView(view);
        snackbar.setAnchorView(binding.btnScanner);
        snackbar.show();
    }

    private void checkAccess() {
        try {

        } catch (Exception e) {
            errorSnackBar("Error : " + e.getMessage());
        }
    }

}