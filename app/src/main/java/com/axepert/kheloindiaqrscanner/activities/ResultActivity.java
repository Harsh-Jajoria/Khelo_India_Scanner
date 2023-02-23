package com.axepert.kheloindiaqrscanner.activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.axepert.kheloindiaqrscanner.R;
import com.axepert.kheloindiaqrscanner.adapters.FOPAdapter;
import com.axepert.kheloindiaqrscanner.databinding.ActivityResultBinding;
import com.axepert.kheloindiaqrscanner.model.response.ScanResponse;
import com.axepert.kheloindiaqrscanner.utils.Constants;
import com.axepert.kheloindiaqrscanner.utils.PreferenceManager;
import com.axepert.kheloindiaqrscanner.viewmodel.ResultViewModel;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity {
    private static final String TAG = "ResultActivity";
    private ActivityResultBinding binding;
    private PreferenceManager preferenceManager;
    ResultViewModel viewModel;
    private FOPAdapter fopAdapter;
    private List<ScanResponse.Data.Access> accessList;
    String result, image, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(ResultViewModel.class);
        preferenceManager = new PreferenceManager(this);
        uid();
        setListener();
        setFOP();
    }

    private void uid() {
        Intent intent = getIntent();
        result = intent.getStringExtra(Constants.KEY_RESULT);
        Log.d(TAG, "uid: " + result);
        if (!result.isEmpty()) {
            try {
                JSONObject obj = new JSONObject(result);
                name = obj.getString("name");
                image = obj.getString("image");
                scan();
            } catch (Throwable t) {
                Log.d(TAG, "Error: " + t.getMessage());
            }

        }
    }

    private void setListener() {
        binding.toolbar.setNavigationOnClickListener(v -> onBackPressed());
        binding.fabScanNew.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra("newCode", 1);
            setResult(101, intent);
            finish();
        });
    }

    private void setFOP() {
        binding.tvAccessCode.setHasFixedSize(true);
        accessList = new ArrayList<>();
        fopAdapter = new FOPAdapter(accessList);
        binding.tvAccessCode.setAdapter(fopAdapter);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void scan() {
        viewModel.scan(
                preferenceManager.getString(Constants.KEY_USER_ID),
                preferenceManager.getString(Constants.KEY_ACCESS_CODE),
                name,
                image).observe(this, scanResponse -> {
            if (scanResponse != null) {
                if (scanResponse.code == 200) {
                    binding.successDialog.setVisibility(View.VISIBLE);
                    binding.tvSuccessMessage.setText(scanResponse.message);
                    binding.progress.setVisibility(View.GONE);
                    binding.cardView.setVisibility(View.VISIBLE);

                    binding.tvName.setText(scanResponse.data.getName());
                    binding.tvSports.setText(scanResponse.data.getSportname());
                    binding.tvState.setText(scanResponse.data.getState());

                    binding.tvTransport.setText(scanResponse.data.getTransport());
                    binding.tvVenue.setText(scanResponse.data.getVenue());
                    binding.tvCategory.setText(scanResponse.data.getCategory());
                    binding.tvCategory.setBackgroundColor(Color.parseColor(scanResponse.data.getCategory_color()));
                    if (scanResponse.data.getDine_in() == 0) {
                        binding.dineImage.setImageResource(R.drawable.dinning_half);
                    } else if (scanResponse.data.getDine_in() == 1) {
                        binding.dineImage.setImageResource(R.drawable.dinning_full);
                    } else {
                        binding.dineImage.setImageResource(0);
                    }

                    binding.imgProfileImage.setAlpha(0f);
                    Picasso.get().load(scanResponse.data.getImageUrl())
                            .noFade()
                            .into(binding.imgProfileImage, new com.squareup.picasso.Callback() {
                                @Override
                                public void onSuccess() {
                                    binding.imgProfileImage.animate().alpha(1f).setDuration(300).start();
                                    binding.imgProgress.setVisibility(View.GONE);
                                }

                                @Override
                                public void onError(Exception e) {
                                    binding.imgProfileImage.setImageResource(R.drawable.profile_image);
                                    binding.imgProfileImage.animate().alpha(1f).setDuration(300).start();
                                    binding.imgProgress.setVisibility(View.GONE);
                                }
                            });
                    accessList.addAll(scanResponse.data.getAccess());
                    fopAdapter.notifyDataSetChanged();
                } else if (scanResponse.code == 401) {
                    binding.errorDialog.setVisibility(View.VISIBLE);
                    binding.tvErrorMessage.setText(scanResponse.message);
                    binding.progress.setVisibility(View.GONE);
                    binding.cardView.setVisibility(View.VISIBLE);
                    binding.tvName.setText(scanResponse.data.getName());
                    binding.tvSports.setText(scanResponse.data.getSportname());
                    binding.tvState.setText(scanResponse.data.getState());

                    binding.tvTransport.setText(scanResponse.data.getTransport());
                    binding.tvVenue.setText(scanResponse.data.getVenue());
                    binding.tvCategory.setText(scanResponse.data.getCategory());
                    binding.tvCategory.setBackgroundColor(Color.parseColor(scanResponse.data.getCategory_color()));
                    if (scanResponse.data.getDine_in() == 0) {
                        binding.dineImage.setImageResource(R.drawable.dinning_half);
                    } else if (scanResponse.data.getDine_in() == 1) {
                        binding.dineImage.setImageResource(R.drawable.dinning_full);
                    } else {
                        binding.dineImage.setImageResource(0);
                    }

                    binding.imgProfileImage.setAlpha(0f);
                    Picasso.get().load(scanResponse.data.getImageUrl())
                            .noFade()
                            .into(binding.imgProfileImage, new com.squareup.picasso.Callback() {
                                @Override
                                public void onSuccess() {
                                    binding.imgProfileImage.animate().alpha(1f).setDuration(300).start();
                                    binding.imgProgress.setVisibility(View.GONE);
                                }

                                @Override
                                public void onError(Exception e) {
                                    binding.imgProfileImage.setImageResource(R.drawable.ic_launcher_background);
                                    binding.imgProfileImage.animate().alpha(1f).setDuration(300).start();
                                    binding.imgProgress.setVisibility(View.GONE);
                                }
                            });
                    accessList.addAll(scanResponse.data.getAccess());
                    fopAdapter.notifyDataSetChanged();
                    binding.progress.setVisibility(View.GONE);
                } else if (scanResponse.code == 402) {
                    binding.warningDialog.setVisibility(View.VISIBLE);
                    binding.tvWarningMessage.setText(scanResponse.message);
                    binding.progress.setVisibility(View.GONE);
                    binding.cardView.setVisibility(View.VISIBLE);
                    binding.tvName.setText(scanResponse.data.getName());
                    binding.tvSports.setText(scanResponse.data.getSportname());
                    binding.tvState.setText(scanResponse.data.getState());

                    binding.tvTransport.setText(scanResponse.data.getTransport());
                    binding.tvVenue.setText(scanResponse.data.getVenue());
                    binding.tvCategory.setText(scanResponse.data.getCategory());
                    binding.tvCategory.setBackgroundColor(Color.parseColor(scanResponse.data.getCategory_color()));
                    if (scanResponse.data.getDine_in() == 0) {
                        binding.dineImage.setImageResource(R.drawable.dinning_half);
                    } else if (scanResponse.data.getDine_in() == 1) {
                        binding.dineImage.setImageResource(R.drawable.dinning_full);
                    } else {
                        binding.dineImage.setImageResource(0);
                    }

                    binding.imgProfileImage.setAlpha(0f);
                    Picasso.get().load(scanResponse.data.getImageUrl())
                            .noFade()
                            .into(binding.imgProfileImage, new com.squareup.picasso.Callback() {
                                @Override
                                public void onSuccess() {
                                    binding.imgProfileImage.animate().alpha(1f).setDuration(300).start();
                                    binding.imgProgress.setVisibility(View.GONE);
                                }

                                @Override
                                public void onError(Exception e) {
                                    binding.imgProfileImage.setImageResource(R.drawable.ic_launcher_background);
                                    binding.imgProfileImage.animate().alpha(1f).setDuration(300).start();
                                    binding.imgProgress.setVisibility(View.GONE);
                                }
                            });
                    accessList.addAll(scanResponse.data.getAccess());
                    fopAdapter.notifyDataSetChanged();
                    binding.progress.setVisibility(View.GONE);
                } else {
                    binding.progress.setVisibility(View.GONE);
                    binding.errorDialog.setVisibility(View.VISIBLE);
                    binding.tvErrorMessage.setText(scanResponse.message);
                }
            } else {
                new AlertDialog.Builder(this)
                        .setTitle("Connection Failed!")
                        .setMessage("Your device is not connected to the internet. Please check your internet connection and try again.")
                        .setCancelable(false)
                        .setPositiveButton("Try Again", (dialog, which) -> {
                            Intent intent = new Intent();
                            intent.putExtra("newCode", 1);
                            setResult(101, intent);
                            finish();
                        })
                        .setNegativeButton("Cancel", (dialog, which) -> finish()).show();
            }
        });
    }
}