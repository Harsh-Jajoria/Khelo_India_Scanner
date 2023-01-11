package com.axepert.kheloindiaqrscanner.activities;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.axepert.kheloindiaqrscanner.R;
import com.google.android.material.snackbar.Snackbar;

public class BaseActivity extends AppCompatActivity {

    public void warningSnackBar(String msg, String heading, View root) {
        Snackbar snackbar = Snackbar.make(root, "", 5000);
        View view = LayoutInflater.from(this).inflate(R.layout.warning_layout, null);
        snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
        Snackbar.SnackbarLayout snackBarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        snackBarLayout.setPadding(0, 0, 0, 0);

        TextView title = view.findViewById(R.id.tvTitle);
        TextView message = view.findViewById(R.id.tvMessage);
        ImageView imgClose = view.findViewById(R.id.imgClose);

        imgClose.setOnClickListener(v -> snackbar.dismiss());

        title.setText(heading);
        message.setText(msg);

        snackBarLayout.addView(view);
        snackbar.show();
    }

    public void errorSnackBar(String msg, String heading, View root) {
        Snackbar snackbar = Snackbar.make(root, "", 5000);
        View view = LayoutInflater.from(this).inflate(R.layout.error_layout, null);
        snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
        Snackbar.SnackbarLayout snackBarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        snackBarLayout.setPadding(0, 0, 0, 0);

        TextView title = view.findViewById(R.id.tvTitle);
        TextView message = view.findViewById(R.id.tvMessage);
        ImageView imgClose = view.findViewById(R.id.imgClose);

        imgClose.setOnClickListener(v -> snackbar.dismiss());

        message.setText(msg);
        title.setText(heading);

        snackBarLayout.addView(view);
        snackbar.show();
    }

    public void successSnackBar(String msg, String heading, View root) {
        Snackbar snackbar = Snackbar.make(root, "", Snackbar.LENGTH_INDEFINITE);
        View view = LayoutInflater.from(this).inflate(R.layout.success_layout, null);
        snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
        Snackbar.SnackbarLayout snackBarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        snackBarLayout.setPadding(0, 0, 0, 0);

        TextView title = view.findViewById(R.id.tvTitle);
        TextView message = view.findViewById(R.id.tvMessage);
        ImageView imgClose = view.findViewById(R.id.imgClose);

        imgClose.setOnClickListener(v -> snackbar.dismiss());

        title.setText(heading);
        message.setText(msg);

        snackBarLayout.addView(view);
        snackbar.show();
    }

}
