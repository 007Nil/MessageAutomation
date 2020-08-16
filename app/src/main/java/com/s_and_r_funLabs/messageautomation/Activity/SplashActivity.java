package com.s_and_r_funLabs.messageautomation.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent changeActivity = new Intent(this, MainActivity.class);
        startActivity(changeActivity);
        finish();
    }
}