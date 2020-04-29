package com.example.ebsma.basic.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.ebsma.basic.views.main.MainActivity;

import maes.tech.intentanim.CustomIntent;


public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        CustomIntent.customType(this,"rotateout-to-rotatein");
        super.finish();
    }

}
