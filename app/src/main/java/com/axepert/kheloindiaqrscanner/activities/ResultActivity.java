package com.axepert.kheloindiaqrscanner.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.axepert.kheloindiaqrscanner.R;
import com.axepert.kheloindiaqrscanner.adapters.FOPAdapter;
import com.axepert.kheloindiaqrscanner.databinding.ActivityResultBinding;
import com.axepert.kheloindiaqrscanner.model.request.ScanRequest;
import com.axepert.kheloindiaqrscanner.model.response.ScanResponse;
import com.axepert.kheloindiaqrscanner.network.ApiClient;
import com.axepert.kheloindiaqrscanner.network.ApiServices;
import com.axepert.kheloindiaqrscanner.utils.Constants;
import com.axepert.kheloindiaqrscanner.utils.PreferenceManager;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultActivity extends AppCompatActivity {
    ActivityResultBinding binding;
    private String result;
    PreferenceManager preferenceManager;
    FOPAdapter fopAdapter;
    List<ScanResponse.Data.Access> accessList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferenceManager = new PreferenceManager(this);
        uid();
        setListener();
        setFOP();
    }

    private void uid() {
        Intent intent = getIntent();
        result = intent.getStringExtra(Constants.KEY_RESULT);
        if (!result.isEmpty()) {
            scanResult();
        }
    }

    private void setListener() {
        binding.toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void setFOP() {
        binding.tvAccessCode.setHasFixedSize(true);
        accessList = new ArrayList<>();
        fopAdapter = new FOPAdapter(accessList, this);
        binding.tvAccessCode.setAdapter(fopAdapter);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void scanResult() {
        binding.progress.setVisibility(View.VISIBLE);
        try {

            ScanRequest scanRequest = new ScanRequest(
                    preferenceManager.getString(Constants.KEY_USER_ID),
                    preferenceManager.getString(Constants.KEY_ACCESS_CODE),
                    result
            );

            ApiClient.getRetrofit().create(ApiServices.class).scanResponse(
                    scanRequest
            ).enqueue(new Callback<ScanResponse>() {
                @Override
                public void onResponse(@NonNull Call<ScanResponse> call, @NonNull Response<ScanResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        if (response.body().code == 200) {
                            binding.successDialog.setVisibility(View.VISIBLE);
                            binding.tvSuccessMessage.setText(response.body().message);
                            binding.progress.setVisibility(View.GONE);
                            binding.cardView.setVisibility(View.VISIBLE);
                            binding.tvName.setText(response.body().data.getName());
                            binding.tvSports.setText(response.body().data.getSportname());
                            binding.tvState.setText(String.format("%s | U18", response.body().data.getState()));
                            binding.imgProfileImage.setAlpha(0f);
                            Picasso.get().load(response.body().data.getImageUrl())
                                    .noFade()
                                    .into(binding.imgProfileImage, new com.squareup.picasso.Callback() {
                                        @Override
                                        public void onSuccess() {
                                            binding.imgProfileImage.animate().alpha(1f).setDuration(300).start();
                                        }

                                        @Override
                                        public void onError(Exception e) {
                                            binding.imgProfileImage.setImageResource(R.drawable.ic_launcher_background);
                                            binding.imgProfileImage.animate().alpha(1f).setDuration(300).start();
                                        }
                                    });
                            accessList.addAll(response.body().data.getAccess());
                            fopAdapter.notifyDataSetChanged();
                        } else if (response.body().code == 402) {
//                            warningSnackBar("You have already visited the zone. You cannot visit twice a day.");
                            binding.warningDialog.setVisibility(View.VISIBLE);
                            binding.tvWarningMessage.setText(response.body().message);
                            binding.progress.setVisibility(View.GONE);
                            binding.cardView.setVisibility(View.VISIBLE);
                            binding.tvName.setText(response.body().data.getName());
                            binding.tvSports.setText(response.body().data.getSportname());
                            binding.tvState.setText(String.format("%s | U18", response.body().data.getState()));
                            binding.imgProfileImage.setAlpha(0f);
                            Picasso.get().load(response.body().data.getImageUrl())
                                    .noFade()
                                    .into(binding.imgProfileImage, new com.squareup.picasso.Callback() {
                                        @Override
                                        public void onSuccess() {
                                            binding.imgProfileImage.animate().alpha(1f).setDuration(300).start();
                                        }

                                        @Override
                                        public void onError(Exception e) {
                                            binding.imgProfileImage.setImageResource(R.drawable.ic_launcher_background);
                                            binding.imgProfileImage.animate().alpha(1f).setDuration(300).start();
                                        }
                                    });
                            accessList.addAll(response.body().data.getAccess());
                            fopAdapter.notifyDataSetChanged();
                            binding.progress.setVisibility(View.GONE);
                        } else if (response.body().code == 401) {
                            binding.errorDialog.setVisibility(View.VISIBLE);
                            binding.tvErrorMessage.setText(response.body().message);
                            binding.progress.setVisibility(View.GONE);
                            binding.cardView.setVisibility(View.VISIBLE);
                            binding.tvName.setText(response.body().data.getName());
                            binding.tvSports.setText(response.body().data.getSportname());
                            binding.tvState.setText(String.format("%s | U18", response.body().data.getState()));
                            binding.imgProfileImage.setAlpha(0f);
                            Picasso.get().load(response.body().data.getImageUrl())
                                    .noFade()
                                    .into(binding.imgProfileImage, new com.squareup.picasso.Callback() {
                                        @Override
                                        public void onSuccess() {
                                            binding.imgProfileImage.animate().alpha(1f).setDuration(300).start();
                                        }

                                        @Override
                                        public void onError(Exception e) {
                                            binding.imgProfileImage.setImageResource(R.drawable.ic_launcher_background);
                                            binding.imgProfileImage.animate().alpha(1f).setDuration(300).start();
                                        }
                                    });
                            accessList.addAll(response.body().data.getAccess());
                            fopAdapter.notifyDataSetChanged();
                            binding.progress.setVisibility(View.GONE);
                        } else {
                            binding.progress.setVisibility(View.GONE);
                            binding.errorDialog.setVisibility(View.VISIBLE);
                            binding.tvErrorMessage.setText(response.body().message);
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ScanResponse> call, @NonNull Throwable t) {
                    binding.progress.setVisibility(View.GONE);
                    binding.errorDialog.setVisibility(View.VISIBLE);
                    binding.tvErrorMessage.setText("Error : " + t.getMessage());
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
            binding.errorDialog.setVisibility(View.VISIBLE);
            binding.tvErrorMessage.setText("Error : " + e.getMessage());
        }

    }


    private void warningSnackBar(String msg) {
        Snackbar snackbar = Snackbar.make(binding.getRoot(), "", Snackbar.LENGTH_INDEFINITE);
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
        snackbar.setAnchorView(binding.cardView);
        snackbar.show();
    }

    private void errorSnackBar(String error) {
        Snackbar snackbar = Snackbar.make(binding.getRoot(), error, Snackbar.LENGTH_INDEFINITE);
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