package com.example.messageautomation.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.messageautomation.Activity.MainActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent changeActivity = new Intent(this, MainActivity.class);
        startActivity(changeActivity);
        finish();
    }
}