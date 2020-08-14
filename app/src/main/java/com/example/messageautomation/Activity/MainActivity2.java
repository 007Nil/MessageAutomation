package com.example.messageautomation.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.messageautomation.R;
import com.example.messageautomation.SharedPreferences.SharedPreferencesCustom;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.example.messageautomation.App.CHANNEL_ID;

public class MainActivity2 extends AppCompatActivity {

    private NotificationManagerCompat notificationManagerCompat;
    private SharedPreferencesCustom sharedPreferencesCustom;
    private String username="",state="",messageStored="";
    private TextView labelEdit,status,message;
    private RadioButton meeting,game,work,awayFromPhone,sleep,available;
    private RadioGroup stateGroup;
    private Intent changeActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

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

        sharedPreferencesCustom = new SharedPreferencesCustom();

        //setup the username
        setUser();

        //setup the mode
        initData();

        stateGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i == R.id.meeting){
                    //saving state in sharedPreference
                    sharedPreferencesCustom.saveData(MainActivity2.this,"state","meeting");
                    //set data to layout
                    setData("meeting");
                    //save the message in sharedPreference
                    sharedPreferencesCustom.saveData(MainActivity2.this,"message",message.getText().toString());
                    //show notification
                    createNotification(MainActivity2.this,status.getText().toString());
                    // modify the audio
                    setAudioTo(status.getText().toString());
                }else if(i == R.id.game){
                    sharedPreferencesCustom.saveData(MainActivity2.this,"state","game");
                    setData("game");
                    sharedPreferencesCustom.saveData(MainActivity2.this,"message",message.getText().toString());
                    createNotification(MainActivity2.this,status.getText().toString());
                    setAudioTo(status.getText().toString());
                }else if(i == R.id.work){
                    sharedPreferencesCustom.saveData(MainActivity2.this,"state","work");
                    setData("work");
                    sharedPreferencesCustom.saveData(MainActivity2.this,"message",message.getText().toString());
                    createNotification(MainActivity2.this,status.getText().toString());
                    setAudioTo(status.getText().toString());
                }else if(i == R.id.awayFromPhone){
                    sharedPreferencesCustom.saveData(MainActivity2.this,"state","driving");
                    setData("driving");
                    sharedPreferencesCustom.saveData(MainActivity2.this,"message",message.getText().toString());
                    createNotification(MainActivity2.this,status.getText().toString());
                    setAudioTo(status.getText().toString());
                }else if(i == R.id.sleep){
                    sharedPreferencesCustom.saveData(MainActivity2.this,"state","sleep");
                    setData("sleep");
                    sharedPreferencesCustom.saveData(MainActivity2.this,"message",message.getText().toString());
                    createNotification(MainActivity2.this,status.getText().toString());
                    setAudioTo(status.getText().toString());
                }else if(i == R.id.available){
                    //checking user opt for available message or not
                    if(sharedPreferencesCustom.loadSendMessageAvailable(MainActivity2.this)){
                        sharedPreferencesCustom.saveData(MainActivity2.this,"state","available");
                        setData("available");
                        sharedPreferencesCustom.saveData(MainActivity2.this,"message",message.getText().toString());
                        //remove notification
                        //deleteNotification();
                        createNotification(MainActivity2.this,status.getText().toString());
                        setAudioTo(status.getText().toString());
                    }else{
                        sharedPreferencesCustom.saveData(MainActivity2.this,"state","available");
                        setData("available");
                        sharedPreferencesCustom.saveData(MainActivity2.this,"message",message.getText().toString());
                        //remove notification
                        //deleteNotification();
                        createNotification(MainActivity2.this,status.getText().toString());
                        setAudioTo(status.getText().toString());
                    }
                }
            }
        });
    }

    private void setData(String string) {
        switch (string) {
            case "meeting":
                status.setText("In a meeting");
                message.setText("Hi there, " + username + " is attending a meeting at the current moment." +
                        " Will get back to you when available." +
                        "\n\nMessage auto-sent from Message Automation.");
                break;
            case "game":
                status.setText("In a game");
                message.setText("Hi there, " + username + " is gaming at the current moment." +
                        " Will get back to you when available." +
                        "\n\nMessage auto-sent from Message Automation.");
                break;
            case "work":
                status.setText("In a work");
                message.setText("Hi there, " + username + " is working at the current moment." +
                        " Will get back to you when available." +
                        "\n\nMessage auto-sent from Message Automation.");
                break;
            case "driving":
                status.setText("Driving");
                message.setText("Hi there, " + username + " is driving at the current moment." +
                        " Will get back to you when available." +
                        "\n\nMessage auto-sent from Message Automation.");
                break;
            case "sleep":
                status.setText("Sleeping");
                message.setText("Hi there, " + username + " is sleeping at the current moment." +
                        " Will get back to you when available." +
                        "\n\nMessage auto-sent from Message Automation.");
                break;
            case "available":
                status.setText("Available");
                if(sharedPreferencesCustom.loadSendMessageAvailable(MainActivity2.this)){
                    message.setText("Sorry " + username + " isn't available." +
                            " Will get back to you as soon as available." +
                            "\n\nMessage auto-sent from Message Automation.");
                }else{
                    message.setText("No message will be send");
                }

                break;
        }
    }

    private void setAudioTo(String string) {
        if(!string.equalsIgnoreCase("available")){
            AudioManager audioManager = (AudioManager)MainActivity2.this.getSystemService(Context.AUDIO_SERVICE);
            audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        }else{
            AudioManager audioManager = (AudioManager)MainActivity2.this.getSystemService(Context.AUDIO_SERVICE);
            int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_RING);

            audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            audioManager.setStreamVolume(AudioManager.STREAM_RING, maxVolume, AudioManager.FLAG_SHOW_UI + AudioManager.FLAG_PLAY_SOUND);
        }
    }

    private void initData() {
        state = sharedPreferencesCustom.loadData(MainActivity2.this);
        messageStored = sharedPreferencesCustom.loadMsg(MainActivity2.this);

        if(state.contains("meeting")){
            meeting.setChecked(true);
            status.setText("In a meeting");
            message.setText(messageStored);
            createNotification(MainActivity2.this,status.getText().toString());
            setAudioTo(status.getText().toString());
        }else if(state.contains("game")){
            game.setChecked(true);
            status.setText("In a game");
            message.setText(messageStored);
            createNotification(MainActivity2.this,status.getText().toString());
            setAudioTo(status.getText().toString());
        }else if(state.contains("work")){
            work.setChecked(true);
            status.setText("In a work");
            message.setText(messageStored);
            createNotification(MainActivity2.this,status.getText().toString());
            setAudioTo(status.getText().toString());
        }else if(state.contains("driving")){
            awayFromPhone.setChecked(true);
            status.setText("Driving");
            message.setText(messageStored);
            createNotification(MainActivity2.this,status.getText().toString());
            setAudioTo(status.getText().toString());
        }else if(state.contains("sleep")){
            sleep.setChecked(true);
            status.setText("Sleeping");
            message.setText(messageStored);
            createNotification(MainActivity2.this,status.getText().toString());
            setAudioTo(status.getText().toString());
        }else if(state.contains("available")){
            available.setChecked(true);
            status.setText("Available");
            createNotification(MainActivity2.this,status.getText().toString());
            //setAudioTo(status.getText().toString());
            if(sharedPreferencesCustom.loadSendMessageAvailable(MainActivity2.this)){
                message.setText("Sorry " + username + " isn't available." +
                        " Will get back to you as soon as available." +
                        "\n\nMessage auto-sent from Message Automation.");
                sharedPreferencesCustom.saveData(MainActivity2.this,"message",message.getText().toString());
            }else{
                message.setText("No message will be send");
                sharedPreferencesCustom.saveData(MainActivity2.this,"message",message.getText().toString());
            }
        }
    }

    private void setUser() {
        username = sharedPreferencesCustom.loadUsername(MainActivity2.this);

        Date time = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("HHmm");
        String currentTime = dateFormat.format(time);
        if(Integer.parseInt(currentTime) >= 0600 && Integer.parseInt(currentTime) <1200){
            labelEdit.setText("Good Morning\n"+username);
        }else if(Integer.parseInt(currentTime) >= 1200 && Integer.parseInt(currentTime) <1600){
            labelEdit.setText("Good Afternoon\n"+username);
        }else{
            labelEdit.setText("Good Evening\n"+username);
        }
    }

    /*private void deleteNotification() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.cancel(1);
        }
    }*/

    private void createNotification(Context context, String status) {
        //Notification Code here
        Intent activityIntent =  new Intent(context,MainActivity2.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context,0,activityIntent,0);

        notificationManagerCompat = NotificationManagerCompat.from(MainActivity2.this);
        Notification notification = new NotificationCompat.Builder(MainActivity2.this,CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_baseline_sms_24)
                .setContentTitle("Message Automation")
                .setContentText("Status : "+status)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setCategory(NotificationCompat.CATEGORY_STATUS)
                .setOngoing(true)
                .setContentIntent(contentIntent)
                .build();

        notificationManagerCompat.notify(1,notification);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.setting:
                changeActivity = new Intent(MainActivity2.this,MainActivity3.class);
                startActivity(changeActivity);
                return true;

            case R.id.about:
                changeActivity = new Intent(MainActivity2.this,MainActivity4.class);
                startActivity(changeActivity);
                return true;

            /*case R.id.checkForUpdate:
                Toast.makeText(MainActivity2.this,"Updater to be implemented",Toast.LENGTH_LONG).show();*/

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        setUser();
        initData();
    }
}