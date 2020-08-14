package com.example.messageautomation.Activity;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
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
import android.telephony.SmsManager;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.messageautomation.R;
import com.example.messageautomation.SharedPreferences.SharedPreferencesCustom;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1;
    private String user;
    private SharedPreferencesCustom sharedPreferencesCustom = new SharedPreferencesCustom();
    private boolean dualSim=true;
    private RadioGroup simRadioGroup;
    private String simName1;
    private String simName2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //sharedPreferencesCustom = new SharedPreferencesCustom();

        askPermission();

        // DND Permissions
        NotificationManager notificationManager =
                (NotificationManager) MainActivity.this.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && !notificationManager.isNotificationPolicyAccessGranted()) {
            //Toast.makeText(this,"Please allow Message Automation for DND permissions",Toast.LENGTH_LONG).show();
            new AlertDialog.Builder(this).setTitle("DND Permission").setMessage("Please allow Message Automation for DND permissions")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(
                                    android.provider.Settings
                                            .ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
                            startActivity(intent);
                        }
                    }).setCancelable(false).create().show();

        }

        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        if (!sharedPreferences.getBoolean("Seen", false)) {
            setContentView(R.layout.activity_main);
            final EditText username;
            Button submit;
            final RadioButton sim1,sim2;
            final CheckBox sendMessageAvailable;
            username = findViewById(R.id.username);
            submit = findViewById(R.id.submit);
            sim1 = findViewById(R.id.sim1);
            sim2 = findViewById(R.id.sim2);
            sendMessageAvailable = findViewById(R.id.sendMessageAvailable);
            simRadioGroup = findViewById(R.id.simRadioGroup);

            if(dualSim){
                sim1.setChecked(true);
                simRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        if(i == R.id.sim1){
                            if(simName1.isEmpty() || simName1.equalsIgnoreCase("no network connection") || simName1.equalsIgnoreCase("emergency calls only")){
                                Toast.makeText(MainActivity.this,"Cannot switch to Sim 1",Toast.LENGTH_LONG).show();
                                sim1.setChecked(false);
                            }else{
                                sim1.setChecked(true);
                            }
                        }else{
                            if(simName2.isEmpty() || simName2.equalsIgnoreCase("no network connection") || simName2.equalsIgnoreCase("emergency calls only")){
                                Toast.makeText(MainActivity.this,"Cannot switch to Sim 2",Toast.LENGTH_LONG).show();
                                sim1.setChecked(true);
                            }else{
                                sim2.setChecked(true);
                            }
                        }
                    }
                });
            }else{
                sim1.setChecked(true);
                sim2.setVisibility(View.INVISIBLE);
            }

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!username.getText().toString().isEmpty()) {
                        SharedPreferences.Editor sPE = PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit();
                        sPE.putBoolean("Seen", true);
                        sPE.apply();

                        user = username.getText().toString();

                        if(sim1.isChecked()){
                            sharedPreferencesCustom.saveData(MainActivity.this,"sim","sim1");

                            if(sendMessageAvailable.isChecked()){
                                sharedPreferencesCustom.saveData(MainActivity.this,"sendMessageAvailable",true);
                            }else{
                                sharedPreferencesCustom.saveData(MainActivity.this,"sendMessageAvailable",false);
                            }

                            Intent changeActivity = new Intent(MainActivity.this, MainActivity2.class);
                            sharedPreferencesCustom.saveData(MainActivity.this,"username",user);
                            startActivity(changeActivity);
                            finish();

                        }else if(sim2.isChecked()){
                            sharedPreferencesCustom.saveData(MainActivity.this,"sim","sim2");

                            if(sendMessageAvailable.isChecked()){
                                sharedPreferencesCustom.saveData(MainActivity.this,"sendMessageAvailable",true);
                            }else{
                                sharedPreferencesCustom.saveData(MainActivity.this,"sendMessageAvailable",false);
                            }

                            Intent changeActivity = new Intent(MainActivity.this, MainActivity2.class);
                            sharedPreferencesCustom.saveData(MainActivity.this,"username",user);
                            startActivity(changeActivity);
                            finish();
                        }else{
                            Toast.makeText(MainActivity.this,"Please select a sim",Toast.LENGTH_LONG).show();
                        }

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

    private void askPermission() {
        // Ask user for required permissions
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED ||ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED
        )  {
            requestPermission();
        }else{
            //check for dual sim
            //dualSim = onDetectDualSim(MainActivity.this);
        }
    }

    @SuppressLint("MissingPermission")
    private boolean onDetectDualSim(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            SubscriptionManager localSubscriptionManager = SubscriptionManager.from(context);
            if (localSubscriptionManager.getActiveSubscriptionInfoCount() > 1) {
                //implement code for 2 sims
                List localList = localSubscriptionManager.getActiveSubscriptionInfoList();
                SubscriptionInfo simInfo1 = (SubscriptionInfo) localList.get(0);
                SubscriptionInfo simInfo2 = (SubscriptionInfo) localList.get(1);
                simName1 = simInfo1.getCarrierName().toString();
                simName2 = simInfo2.getCarrierName().toString();
                dualSim = true;
            }else{
                //implement code for only 1 sim
                List localList = localSubscriptionManager.getActiveSubscriptionInfoList();
                SubscriptionInfo simInfo1 = (SubscriptionInfo) localList.get(0);
                simName1 = simInfo1.getCarrierName().toString();
                dualSim = false;
            }
        }
        return dualSim;
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
                        /*Toast.makeText(MainActivity.this,
                                "Permission accepted for"+permissions[count], Toast.LENGTH_SHORT).show();*/
                    } else {
                        Toast.makeText(MainActivity.this,
                                "Permission denied for "+permissions[count], Toast.LENGTH_SHORT).show();
                    }
                    count++;
                }

                dualSim = onDetectDualSim(MainActivity.this);

            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case RESULT_OK:
                askPermission();
                break;
            default:
                Toast.makeText(this,"Permission denied",Toast.LENGTH_LONG).show();
        }
    }
}