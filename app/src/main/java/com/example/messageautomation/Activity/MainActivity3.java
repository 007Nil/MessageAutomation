package com.example.messageautomation.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.messageautomation.R;
import com.example.messageautomation.SharedPreferences.SharedPreferencesCustom;

public class MainActivity3 extends AppCompatActivity {
    private TextView setting1;
    private SwitchCompat setting2;
    private SharedPreferencesCustom sharedPreferencesCustom;
    private boolean sendMessageAvailable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        setting1 = findViewById(R.id.setting1);
        setting2 = findViewById(R.id.setting2);
        sharedPreferencesCustom = new SharedPreferencesCustom();

        sendMessageAvailable = sharedPreferencesCustom.loadSendMessageAvailable(MainActivity3.this);

        if(sendMessageAvailable){
            setting2.setChecked(true);
        }else{
            setting2.setChecked(false);
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
    }
}