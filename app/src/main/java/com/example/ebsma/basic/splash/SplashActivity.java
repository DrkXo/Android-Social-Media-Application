package com.example.ebsma.basic.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ebsma.basic.R;
import com.example.ebsma.basic.views.main.MainActivity;

import maes.tech.intentanim.CustomIntent;


public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                CustomIntent.customType(SplashActivity.this, "fadein-to-fadeout");
                finish();

            }
        }, 1500);
    }

}
