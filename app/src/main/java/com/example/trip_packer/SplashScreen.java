package com.example.trip_packer;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {
    private ImageView appIcon;
    private TextView txtAppName;
    private TextView txtFrom;
    private TextView txtBtechMates;
    private ImageView logo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(SplashScreen.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },1400);


        appIcon = findViewById(R.id.appIcon);
        txtAppName = findViewById(R.id.txtAppName);
        txtFrom = findViewById(R.id.txtFrom);
        txtBtechMates = findViewById(R.id.txtBtechMates);
        logo = findViewById(R.id.logo);
        startAnimation();
    }

    private void startAnimation() {

        ObjectAnimator fadeInAppIcon = ObjectAnimator.ofFloat(appIcon, "alpha", 0f, 1f);
        fadeInAppIcon.setDuration(3000);

        ObjectAnimator fadeInTxtAppName = ObjectAnimator.ofFloat(txtAppName, "alpha", 0f, 1f);
        fadeInTxtAppName.setDuration(30000);

        ObjectAnimator fadeInTxtFrom = ObjectAnimator.ofFloat(txtFrom, "alpha", 0f, 1f);
        fadeInTxtFrom.setDuration(3000);

        ObjectAnimator fadeInTxtBtechMates = ObjectAnimator.ofFloat(txtBtechMates, "alpha", 0f, 1f);
        fadeInTxtBtechMates.setDuration(3000);

        ObjectAnimator fadeInLogo = ObjectAnimator.ofFloat(logo, "alpha", 0f, 1f);
        fadeInLogo.setDuration(3000);


        android.animation.AnimatorSet animatorSet = new android.animation.AnimatorSet();
        animatorSet.playTogether(fadeInAppIcon, fadeInTxtAppName, fadeInTxtFrom, fadeInTxtBtechMates, fadeInLogo);
        animatorSet.setInterpolator(new AccelerateInterpolator());
        animatorSet.start();
    }
}




