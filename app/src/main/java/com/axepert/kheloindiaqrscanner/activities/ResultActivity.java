package com.axepert.kheloindiaqrscanner.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.axepert.kheloindiaqrscanner.R;
import com.axepert.kheloindiaqrscanner.databinding.ActivityResultBinding;
import com.axepert.kheloindiaqrscanner.model.request.ScanRequest;
import com.axepert.kheloindiaqrscanner.utils.Constants;
import com.axepert.kheloindiaqrscanner.utils.PreferenceManager;
import com.google.android.material.snackbar.Snackbar;

public class ResultActivity extends AppCompatActivity {
    ActivityResultBinding binding;
    private String result;
    PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        uid();
        setListener();
    }

    private void uid() {
        Intent intent = getIntent();
        result = intent.getStringExtra(Constants.KEY_RESULT);
    }

    private void setListener() {
        binding.toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void scanResult() {

        try {

            ScanRequest scanRequest = new ScanRequest(
                    "",
                    "",
                    ""
            );
        } catch (Exception e) {
            e.printStackTrace();
            errorSnackBar("Error : " + e.getMessage());
        }

    }



    private void warningSnackBar(String msg) {
        Snackbar snackbar = Snackbar.make(binding.getRoot(), "", 5000);
        View view = LayoutInflater.from(this).inflate(R.layout.warning_layout, null);
        snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
        Snackbar.SnackbarLayout snackBarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        snackBarLayout.setPadding(0, 0, 0, 0);

        TextView title = view.findViewById(R.id.tvTitle);
        TextView message = view.findViewById(R.id.tvMessage);
        ImageView imgClose = view.findViewById(R.id.imgClose);

        imgClose.setOnClickListener(v -> snackbar.dismiss());

        message.setText(msg);

        snackBarLayout.addView(view);
        snackbar.show();
    }

    private void errorSnackBar(String error) {
        Snackbar snackbar = Snackbar.make(binding.getRoot(), error, 5000);
        View view = LayoutInflater.from(this).inflate(R.layout.error_layout, null);
        snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
        Snackbar.SnackbarLayout snackBarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        snackBarLayout.setPadding(0, 0, 0, 0);

        TextView title = view.findViewById(R.id.tvTitle);
        TextView message = view.findViewById(R.id.tvMessage);
        ImageView imgClose = view.findViewById(R.id.imgClose);

        imgClose.setOnClickListener(v -> snackbar.dismiss());

        message.setText(error);

        snackBarLayout.addView(view);
        snackbar.show();
    }

    private void successSnackBar(String msg) {
        Snackbar snackbar = Snackbar.make(binding.getRoot(), "", Snackbar.LENGTH_INDEFINITE);
        View view = LayoutInflater.from(this).inflate(R.layout.success_layout, null);
        snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
        Snackbar.SnackbarLayout snackBarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        snackBarLayout.setPadding(0, 0, 0, 0);

        TextView title = view.findViewById(R.id.tvTitle);
        TextView message = view.findViewById(R.id.tvMessage);
        ImageView imgClose = view.findViewById(R.id.imgClose);

        imgClose.setOnClickListener(v -> snackbar.dismiss());

        message.setText(msg);

        snackBarLayout.addView(view);
        snackbar.show();
    }

}