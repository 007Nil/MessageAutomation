package com.example.messageautomation.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.messageautomation.R;

public class MainActivity2 extends AppCompatActivity {

    private static final String SHARED_PREFS = "sharedPrefs";
    private static final String KEY = "state";

    public static void saveData(Context context,String text) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY, text);
        editor.apply();
    }

    public static String loadData(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String text = sharedPreferences.getString(KEY, "available");
        return text;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        String username="",state="";
        final TextView labelEdit,status,message;
        RadioButton meeting,game,work,awayFromPhone,sleep,available;
        RadioGroup stateGroup;

        username = PreferenceManager.getDefaultSharedPreferences(MainActivity2.this).getString("username","");

        labelEdit = findViewById(R.id.labelEdit);

        meeting = findViewById(R.id.meeting);
        game = findViewById(R.id.game);
        work = findViewById(R.id.work);
        awayFromPhone = findViewById(R.id.awayFromPhone);
        sleep = findViewById(R.id.sleep);
        available = findViewById(R.id.available);

        stateGroup = findViewById(R.id.stateGroup);

        status = findViewById(R.id.status);

        message = findViewById(R.id.message);

        labelEdit.setText("Hello "+username);

        state = loadData(MainActivity2.this);

        if(state.contains("meeting")){
            meeting.setChecked(true);
            status.setText("In a meeting");
            message.setText("Hi there, " + username + " is attending a meeting at the current moment. He will get back to you when available." +
                    "\n\nThank You\nChatBot");
        }else if(state.contains("game")){
            game.setChecked(true);
            status.setText("In a game");
            message.setText("Hi there, " + username + " is gaming at the current moment. He will get back to you when available." +
                    "\n\nThank You\nChatBot");
        }else if(state.contains("work")){
            work.setChecked(true);
            status.setText("In a work");
            message.setText("Hi there, " + username + " is working at the current moment. He will get back to you when available." +
                    "\n\nThank You\nChatBot");
        }else if(state.contains("awayFromPhone")){
            awayFromPhone.setChecked(true);
            status.setText("Away from phone");
            message.setText("Hi there, " + username + " is away from phone at the current moment. He will get back to you when available." +
                    "\n\nThank You\nChatBot");
        }else if(state.contains("sleep")){
            sleep.setChecked(true);
            status.setText("Sleeping");
            message.setText("Hi there, " + username + " is sleeping at the current moment. He will get back to you when available." +
                    "\n\nThank You\nChatBot");
        }else if(state.contains("available")){
            available.setChecked(true);
            status.setText("Available");
            message.setText("No message will be send");
        }

        final String finalUsername = username;

        stateGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i == R.id.meeting){
                    AudioManager audioManager = (AudioManager)MainActivity2.this.getSystemService(Context.AUDIO_SERVICE);
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);

                    saveData(MainActivity2.this,"meeting");
                    status.setText("In a meeting");
                    message.setText("Hi there, " + finalUsername + " is attending a meeting at the current moment. He will get back to you when available." +
                            "\n\nThank You\nChatBot");
                }else if(i == R.id.game){
                    AudioManager audioManager = (AudioManager)MainActivity2.this.getSystemService(Context.AUDIO_SERVICE);
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);

                    saveData(MainActivity2.this,"game");
                    status.setText("In a game");
                    message.setText("Hi there, " + finalUsername + " is gaming at the current moment. He will get back to you when available." +
                            "\n\nThank You\nChatBot");
                }else if(i == R.id.work){
                    AudioManager audioManager = (AudioManager)MainActivity2.this.getSystemService(Context.AUDIO_SERVICE);
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);

                    saveData(MainActivity2.this,"work");
                    status.setText("In a work");
                    message.setText("Hi there, " + finalUsername + " is working at the current moment. He will get back to you when available." +
                            "\n\nThank You\nChatBot");
                }else if(i == R.id.awayFromPhone){
                    AudioManager audioManager = (AudioManager)MainActivity2.this.getSystemService(Context.AUDIO_SERVICE);
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);

                    saveData(MainActivity2.this,"awayFromPhone");
                    status.setText("Away from phone");
                    message.setText("Hi there, " + finalUsername + " is away from phone at the current moment. He will get back to you when available." +
                            "\n\nThank You\nChatBot");
                }else if(i == R.id.sleep){
                    AudioManager audioManager = (AudioManager)MainActivity2.this.getSystemService(Context.AUDIO_SERVICE);
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);

                    saveData(MainActivity2.this,"sleep");
                    status.setText("Sleeping");
                    message.setText("Hi there, " + finalUsername + " is sleeping at the current moment. He will get back to you when available." +
                            "\n\nThank You\nChatBot");
                }else if(i == R.id.available){

                    AudioManager audioManager = (AudioManager)MainActivity2.this.getSystemService(Context.AUDIO_SERVICE);
                    int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_RING);

                    audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                    audioManager.setStreamVolume(AudioManager.STREAM_RING, maxVolume, AudioManager.FLAG_SHOW_UI + AudioManager.FLAG_PLAY_SOUND);

                    saveData(MainActivity2.this,"available");
                    status.setText("Available");
                    message.setText("No message will be send");
                }
            }
        });

    }
}