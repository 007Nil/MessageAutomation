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
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.messageautomation.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.example.messageautomation.App.CHANNEL_ID;

public class MainActivity2 extends AppCompatActivity {

    private static final String SHARED_PREFS = "sharedPrefs";
    private NotificationManagerCompat notificationManagerCompat;

    public static void saveData(Context context,String key,String text) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, text);
        editor.apply();
    }

    public static String loadData(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String text = sharedPreferences.getString("state", "available");
        return text;
    }

    public static String loadMsg(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String text = sharedPreferences.getString("message", "Sorry the owner isn't available." +
                " Will get back to you as soon as available." +
                "\n\nMessage auto-sent from Message Automation.");
        return text;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        String username="",state="",messageStored="";
        final TextView labelEdit,status,message;
        RadioButton meeting,game,work,awayFromPhone,sleep,available;
        RadioGroup stateGroup;

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

        username = PreferenceManager.getDefaultSharedPreferences(MainActivity2.this).getString("username","");

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

        state = loadData(MainActivity2.this);
        messageStored = loadMsg(MainActivity2.this);

        if(state.contains("meeting")){
            meeting.setChecked(true);
            status.setText("In a meeting");
            message.setText(messageStored);
        }else if(state.contains("game")){
            game.setChecked(true);
            status.setText("In a game");
            message.setText(messageStored);
        }else if(state.contains("work")){
            work.setChecked(true);
            status.setText("In a work");
            message.setText(messageStored);
        }else if(state.contains("awayFromPhone")){
            awayFromPhone.setChecked(true);
            status.setText("Driving");
            message.setText(messageStored);
        }else if(state.contains("sleep")){
            sleep.setChecked(true);
            status.setText("Sleeping");
            message.setText(messageStored);
        }else if(state.contains("available")){
            available.setChecked(true);
            status.setText("Available");
            message.setText(messageStored);
        }

        final String finalUsername = username;

        stateGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i == R.id.meeting){
                    AudioManager audioManager = (AudioManager)MainActivity2.this.getSystemService(Context.AUDIO_SERVICE);
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);

                    saveData(MainActivity2.this,"state","meeting");
                    status.setText("In a meeting");
                    message.setText("Hi there, " + finalUsername + " is attending a meeting at the current moment. Will get back to you when available." +
                            "\n\nMessage auto-sent from Message Automation.");
                    saveData(MainActivity2.this,"message",message.getText().toString());

                    createNotification(MainActivity2.this,status.getText().toString());
                }else if(i == R.id.game){
                    AudioManager audioManager = (AudioManager)MainActivity2.this.getSystemService(Context.AUDIO_SERVICE);
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);

                    saveData(MainActivity2.this,"state","game");
                    status.setText("In a game");
                    message.setText("Hi there, " + finalUsername + " is gaming at the current moment. Will get back to you when available." +
                            "\n\nMessage auto-sent from Message Automation.");
                    saveData(MainActivity2.this,"message",message.getText().toString());

                    createNotification(MainActivity2.this,status.getText().toString());
                }else if(i == R.id.work){
                    AudioManager audioManager = (AudioManager)MainActivity2.this.getSystemService(Context.AUDIO_SERVICE);
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);

                    saveData(MainActivity2.this,"state","work");
                    status.setText("In a work");
                    message.setText("Hi there, " + finalUsername + " is working at the current moment. Will get back to you when available." +
                            "\n\nMessage auto-sent from Message Automation.");
                    saveData(MainActivity2.this,"message",message.getText().toString());

                    createNotification(MainActivity2.this,status.getText().toString());
                }else if(i == R.id.awayFromPhone){
                    AudioManager audioManager = (AudioManager)MainActivity2.this.getSystemService(Context.AUDIO_SERVICE);
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);

                    saveData(MainActivity2.this,"state","awayFromPhone");
                    status.setText("Driving");
                    message.setText("Hi there, " + finalUsername + " is driving at the current moment. Will get back to you when available." +
                            "\n\nMessage auto-sent from Message Automation.");
                    saveData(MainActivity2.this,"message",message.getText().toString());

                    createNotification(MainActivity2.this,status.getText().toString());
                }else if(i == R.id.sleep){
                    AudioManager audioManager = (AudioManager)MainActivity2.this.getSystemService(Context.AUDIO_SERVICE);
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);

                    saveData(MainActivity2.this,"state","sleep");
                    status.setText("Sleeping");
                    message.setText("Hi there, " + finalUsername + " is sleeping at the current moment. Will get back to you when available." +
                            "\n\nMessage auto-sent from Message Automation.");
                    saveData(MainActivity2.this,"message",message.getText().toString());

                    createNotification(MainActivity2.this,status.getText().toString());
                }else if(i == R.id.available){

                    AudioManager audioManager = (AudioManager)MainActivity2.this.getSystemService(Context.AUDIO_SERVICE);
                    int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_RING);

                    audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                    audioManager.setStreamVolume(AudioManager.STREAM_RING, maxVolume, AudioManager.FLAG_SHOW_UI + AudioManager.FLAG_PLAY_SOUND);

                    saveData(MainActivity2.this,"state","available");
                    status.setText("Available");
                    message.setText("Sorry the owner isn't available." +
                            " Will get back to you as soon as available." +
                            "\n\nMessage auto-sent from Message Automation.");
                    saveData(MainActivity2.this,"message",message.getText().toString());

                    deleteNotification();
                }
            }
        });
    }

    private void deleteNotification() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.cancel(1);
        }
    }

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
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity2.this);
                builder.setTitle("Change Username");
                final EditText input = new EditText(MainActivity2.this);
                final TextView label = new TextView(MainActivity2.this);
                label.setText("Enter new name");
                label.setTextSize(20);
                label.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                input.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
                LinearLayout layout = new LinearLayout(this);
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
                        PreferenceManager.getDefaultSharedPreferences(MainActivity2.this).edit().putString("username", input.getText().toString()).apply();
                        finish();
                        startActivity(getIntent());
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
                return true;

            case R.id.about:
                Toast.makeText(MainActivity2.this,"App developed by Rounak and Sagnik" +
                        "\n\nCredits Page yet to be implemented!",Toast.LENGTH_LONG).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}