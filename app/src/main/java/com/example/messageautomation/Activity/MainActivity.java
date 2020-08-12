package com.example.messageautomation.Activity;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.messageautomation.R;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        NotificationManager notificationManager =
                (NotificationManager) MainActivity.this.getSystemService(Context.NOTIFICATION_SERVICE);

        // Ask user for required permissions
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED ||ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED
        )  {
            requestPermission();
        }


//        DND Permissions
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && !notificationManager.isNotificationPolicyAccessGranted()) {
            Toast.makeText(this,"Please allow Message Automation for DND permissions",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(
                    android.provider.Settings
                            .ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
            startActivity(intent);
        }

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        if (!sharedPreferences.getBoolean("Seen", false)) {
            setContentView(R.layout.activity_main);
            final EditText username;
            Button submit;

            username = findViewById(R.id.username);
            submit = findViewById(R.id.submit);

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!username.getText().toString().isEmpty()) {
                        SharedPreferences.Editor sPE = PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit();
                        sPE.putBoolean("Seen", true);
                        sPE.apply();

                        Intent changeActivity = new Intent(MainActivity.this, MainActivity2.class);
                        PreferenceManager.getDefaultSharedPreferences(MainActivity.this).edit().putString("username", username.getText().toString()).apply();
                        startActivity(changeActivity);
                        finish();
                    } else {
                        Toast.makeText(MainActivity.this, "Please enter your name to continue", Toast.LENGTH_LONG).show();
                    }
                }
            });
        } else {
            Intent changeActivity = new Intent(MainActivity.this, MainActivity2.class);
            startActivity(changeActivity);
            finish();
        }
    }

    private void requestPermission() {

        if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.SEND_SMS)){

            new AlertDialog.Builder(this).setTitle("Permission needed")
                    .setMessage("This is needed")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{Manifest.permission.SEND_SMS,Manifest.permission.CALL_PHONE,
                                                 Manifest.permission.READ_CALL_LOG,Manifest.permission.READ_PHONE_STATE}
                                    , PERMISSION_REQUEST_CODE);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .create().show();



        }else{
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.SEND_SMS, Manifest.permission.CALL_PHONE,
                             Manifest.permission.READ_CALL_LOG,Manifest.permission.READ_PHONE_STATE}
                , PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {
                int count = 0;
                for (int each : grantResults) {
                    if (each == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(MainActivity.this,
                                "Permission accepted for"+permissions[count], Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this,
                                "Permission denied for "+permissions[count], Toast.LENGTH_SHORT).show();
                    }
                    count++;
                }

            }
        }
    }
}