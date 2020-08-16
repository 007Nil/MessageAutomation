package com.s_and_r_funLabs.messageautomation.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.text.InputType;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.s_and_r_funLabs.messageautomation.R;
import com.s_and_r_funLabs.messageautomation.SharedPreferences.SharedPreferencesCustom;

import java.util.List;

public class MainActivity3 extends AppCompatActivity {
    private TextView setting1;
    private SwitchCompat setting2,enableService;
    private SharedPreferencesCustom sharedPreferencesCustom;
    private boolean sendMessageAvailable;
    private RadioGroup simRadioGroup;
    private RadioButton sim1,sim2;
    private String simName1;
    private String simName2;
    private boolean dualSim=true;
    private String sim;
    private String state;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        setting1 = findViewById(R.id.setting1);
        setting2 = findViewById(R.id.setting2);
        simRadioGroup = findViewById(R.id.simRadioGroup);
        sim1 = findViewById(R.id.sim1);
        sim2 = findViewById(R.id.sim2);
        sharedPreferencesCustom = new SharedPreferencesCustom();
        enableService = findViewById(R.id.enableService);

        sendMessageAvailable = sharedPreferencesCustom.loadSendMessageAvailable(MainActivity3.this);

        if(sendMessageAvailable){
            setting2.setChecked(true);
        }else{
            setting2.setChecked(false);
        }

        state = sharedPreferencesCustom.loadData(MainActivity3.this);

        if(state.equalsIgnoreCase("off")){
            enableService.setChecked(false);
        }else{
            enableService.setChecked(true);
        }

        this.getSupportActionBar().setTitle("Settings");

        setting1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity3.this);
                builder.setTitle("Change Username");
                final EditText input = new EditText(MainActivity3.this);
                final TextView label = new TextView(MainActivity3.this);
                label.setText("Enter new name");
                label.setTextSize(20);
                label.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                input.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
                LinearLayout layout = new LinearLayout(MainActivity3.this);
                layout.setOrientation(LinearLayout.VERTICAL);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(50, 30, 50, 0);
                layout.addView(label,params);
                layout.addView(input,params);
                builder.setView(layout);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sharedPreferencesCustom.saveData(MainActivity3.this,"username",input.getText().toString());
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.setCancelable(false);
                builder.show();
            }
        });

        setting2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(setting2.isChecked()){
                    sharedPreferencesCustom.saveData(MainActivity3.this,"sendMessageAvailable",true);
                }else{
                    sharedPreferencesCustom.saveData(MainActivity3.this,"sendMessageAvailable",false);
                }
            }
        });

        enableService.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(enableService.isChecked()){
                    sharedPreferencesCustom.saveData(MainActivity3.this,"state","available");
                }else{
                    sharedPreferencesCustom.saveData(MainActivity3.this,"state","off");
                }
            }
        });

        //load sim data form the shared preference
        sim = sharedPreferencesCustom.loadSim(MainActivity3.this);
        if(sim.equalsIgnoreCase("sim1")){
            sim1.setChecked(true);
        }else{
            sim2.setChecked(true);
        }

        //check for dual sim
        dualSim = onDetectDualSim(MainActivity3.this);
        if(dualSim){
            sim1.setChecked(true);
            simRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    if(i == R.id.sim1){
                        if(simName1.isEmpty() || simName1.equalsIgnoreCase("no network connection") || simName1.equalsIgnoreCase("emergency calls only")){
                            Toast.makeText(MainActivity3.this,"Cannot switch to Sim 1",Toast.LENGTH_LONG).show();
                            sim1.setChecked(false);
                            sharedPreferencesCustom.saveData(MainActivity3.this,"sim","sim2");
                        }else{
                            sim1.setChecked(true);
                            sharedPreferencesCustom.saveData(MainActivity3.this,"sim","sim1");
                        }
                    }else{
                        if(simName2.isEmpty() || simName2.equalsIgnoreCase("no network connection") || simName2.equalsIgnoreCase("emergency calls only")){
                            Toast.makeText(MainActivity3.this,"Cannot switch to Sim 2",Toast.LENGTH_LONG).show();
                            sim1.setChecked(true);
                            sharedPreferencesCustom.saveData(MainActivity3.this,"sim","sim1");
                        }else{
                            sim2.setChecked(true);
                            sharedPreferencesCustom.saveData(MainActivity3.this,"sim","sim2");
                        }
                    }
                }
            });
        }else{
            sim1.setChecked(true);
            sim2.setVisibility(View.INVISIBLE);
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

    @Override
    protected void onStart() {
        super.onStart();
        dualSim = onDetectDualSim(MainActivity3.this);
    }
}