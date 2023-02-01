package com.axepert.kheloindiaqrscanner.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;

import com.axepert.kheloindiaqrscanner.R;
import com.axepert.kheloindiaqrscanner.databinding.ActivityMainBinding;
import com.axepert.kheloindiaqrscanner.utils.Constants;
import com.axepert.kheloindiaqrscanner.utils.PreferenceManager;
import com.google.mlkit.common.MlKitException;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner;
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning;
import com.journeyapps.barcodescanner.CaptureActivity;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

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
        requestPermission();
        setProfileValues();
        setListener();
    }

    private void requestPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, 101);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Camera permission required")
                        .setPositiveButton("Allow", (dialog, which) -> {
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getPackageName(), null);
                            intent.setData(uri);
                            startActivity(intent);
                        })
                        .setNegativeButton("Don't Allow", (dialog, which) -> dialog.dismiss()).show();
            }
        }
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
        binding.btnScanner.setOnClickListener(v -> {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 101);
            } else {
                scanCode();
            }
        });
        binding.imgLogout.setOnClickListener(v -> {
            preferenceManager.clear();
            startActivity(new Intent(MainActivity.this, LoginActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        });
    }

    private ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == 101) {
                    Intent intent = result.getData();
                    if (intent != null) {
                        int resultFromIntent = intent.getIntExtra("newCode", -1);
                        if (resultFromIntent == 1) {
                            scanCode();
                        }
                    }
                }
            }
    );

    private void scanCode() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        resultLauncher.launch(options);
    }

    private final ActivityResultLauncher<ScanOptions> resultLauncher = registerForActivityResult(
            new ScanContract(),
            result -> {
                if (result.getContents() != null) {
                    if (result.getContents().contains("KIYG2022")) {
                        Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                        intent.putExtra(Constants.KEY_RESULT, result.getContents());
                        launcher.launch(intent);
                    } else {
                        Toast.makeText(this, "QR Code is not valid!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

}