package com.example.messageautomation.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.messageautomation.R;

import java.util.prefs.PreferenceChangeEvent;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        NotificationManager notificationManager =
                (NotificationManager) MainActivity.this.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && !notificationManager.isNotificationPolicyAccessGranted()) {

            Intent intent = new Intent(
                    android.provider.Settings
                            .ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);

            startActivity(intent);
        }

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        if(!sharedPreferences.getBoolean("Seen",false)){
            setContentView(R.layout.activity_main);
            final EditText username;
            Button submit;

            username = findViewById(R.id.username);
            submit = findViewById(R.id.submit);

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences.Editor sPE = PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit();
                    sPE.putBoolean("Seen",true);
                    sPE.apply();

                    if(!username.getText().toString().isEmpty()){
                        Intent changeActivity = new Intent(MainActivity.this, MainActivity2.class);
                        PreferenceManager.getDefaultSharedPreferences(MainActivity.this).edit().putString("username",username.getText().toString()).apply();
                        startActivity(changeActivity);
                        finish();
                    }else{
                        Toast.makeText(MainActivity.this,"Please enter your name to continue",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }else{
            Intent changeActivity = new Intent(MainActivity.this, MainActivity2.class);
            startActivity(changeActivity);
            finish();
        }
    }
}