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
    private RadioButton meeting,game,work,driving,sleep,available;
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
        driving = findViewById(R.id.driving);
        sleep = findViewById(R.id.sleep);
        available = findViewById(R.id.available);
        stateGroup = findViewById(R.id.stateGroup);
        status = findViewById(R.id.status);
        message = findViewById(R.id.message);

        sharedPreferencesCustom = new SharedPreferencesCustom();


    }

    private void setData(String string) {
        switch (string) {
            case "meeting":
                status.setText(R.string.in_a_meeting);
                message.setText(String.format(getResources().getString(R.string.messageToBeSend),username,"attending a meeting"));
                break;
            case "game":
                status.setText(R.string.in_a_game);
                message.setText(String.format(getResources().getString(R.string.messageToBeSend),username,"gaming"));
                break;
            case "work":
                status.setText(R.string.in_a_work);
                message.setText(String.format(getResources().getString(R.string.messageToBeSend),username,"working"));
                break;
            case "driving":
                status.setText(R.string.driving);
                message.setText(String.format(getResources().getString(R.string.messageToBeSend),username,"driving"));
                break;
            case "sleep":
                status.setText(R.string.sleeping);
                message.setText(String.format(getResources().getString(R.string.messageToBeSend),username,"sleeping"));
                break;
            case "available":
                status.setText(R.string.available);
                if(sharedPreferencesCustom.loadSendMessageAvailable(MainActivity2.this)){
                    message.setText(String.format(getResources().getString(R.string.sendMessageAvailable),username));
                }else{
                    message.setText(R.string.no_message_send);
                }
                break;

            case "off":
                status.setText(R.string.stopped);
                message.setText("");
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

    private void initData(String state, String messageStored) {
        switch (state){
            case "meeting":
                meeting.setChecked(true);
                status.setText(R.string.in_a_meeting);
                message.setText(messageStored);
                createNotification(MainActivity2.this,status.getText().toString());
                setAudioTo(status.getText().toString());
                break;
            case "game":
                game.setChecked(true);
                status.setText(R.string.in_a_game);
                message.setText(messageStored);
                createNotification(MainActivity2.this,status.getText().toString());
                setAudioTo(status.getText().toString());
                break;
            case "work":
                work.setChecked(true);
                status.setText(R.string.in_a_work);
                message.setText(messageStored);
                createNotification(MainActivity2.this,status.getText().toString());
                setAudioTo(status.getText().toString());
                break;
            case "driving":
                driving.setChecked(true);
                status.setText(R.string.driving);
                message.setText(messageStored);
                createNotification(MainActivity2.this,status.getText().toString());
                setAudioTo(status.getText().toString());
                break;
            case "sleep":
                sleep.setChecked(true);
                status.setText(R.string.sleeping);
                message.setText(messageStored);
                createNotification(MainActivity2.this,status.getText().toString());
                setAudioTo(status.getText().toString());
                break;
            case "available":
                available.setChecked(true);
                status.setText(R.string.available);
                createNotification(MainActivity2.this,status.getText().toString());
                if(sharedPreferencesCustom.loadSendMessageAvailable(MainActivity2.this)){
                    message.setText(String.format(getResources().getString(R.string.sendMessageAvailable),username));
                    sharedPreferencesCustom.saveData(MainActivity2.this,"message",message.getText().toString());
                }else{
                    message.setText(R.string.no_message_send);
                    sharedPreferencesCustom.saveData(MainActivity2.this,"message",message.getText().toString());
                }
                break;
            case "off":
                status.setText(R.string.stopped);
                message.setText("");
                deleteNotification();
                break;
        }
    }

    private void setUser() {
        username = sharedPreferencesCustom.loadUsername(MainActivity2.this);

        Date time = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("HHmm");
        String currentTime = dateFormat.format(time);
        if(Integer.parseInt(currentTime) >= 0600 && Integer.parseInt(currentTime) <1200){
            labelEdit.setText(String.format(getResources().getString(R.string.greetings),"Good Morning",username));
        }else if(Integer.parseInt(currentTime) >= 1200 && Integer.parseInt(currentTime) <1600){
            labelEdit.setText(String.format(getResources().getString(R.string.greetings),"Good Afternoon",username));
        }else{
            labelEdit.setText(String.format(getResources().getString(R.string.greetings),"Good Evening",username));
        }
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
                changeActivity = new Intent(MainActivity2.this,MainActivity3.class);
                startActivity(changeActivity);
                return true;

            case R.id.about:
                changeActivity = new Intent(MainActivity2.this,MainActivity4.class);
                startActivity(changeActivity);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        state = sharedPreferencesCustom.loadData(MainActivity2.this);
        messageStored = sharedPreferencesCustom.loadMsg(MainActivity2.this);

        //setup the username
        setUser();
        //setup the mode
        initData(state,messageStored);

        if(!state.equalsIgnoreCase("off")){
            stateGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {

                    switch (i){
                        case R.id.meeting:
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
                            break;

                        case R.id.game:
                            sharedPreferencesCustom.saveData(MainActivity2.this,"state","game");
                            setData("game");
                            sharedPreferencesCustom.saveData(MainActivity2.this,"message",message.getText().toString());
                            createNotification(MainActivity2.this,status.getText().toString());
                            setAudioTo(status.getText().toString());
                            break;

                        case R.id.work:
                            sharedPreferencesCustom.saveData(MainActivity2.this,"state","work");
                            setData("work");
                            sharedPreferencesCustom.saveData(MainActivity2.this,"message",message.getText().toString());
                            createNotification(MainActivity2.this,status.getText().toString());
                            setAudioTo(status.getText().toString());
                            break;

                        case R.id.driving:
                            sharedPreferencesCustom.saveData(MainActivity2.this,"state","driving");
                            setData("driving");
                            sharedPreferencesCustom.saveData(MainActivity2.this,"message",message.getText().toString());
                            createNotification(MainActivity2.this,status.getText().toString());
                            setAudioTo(status.getText().toString());
                            break;

                        case R.id.sleep:
                            sharedPreferencesCustom.saveData(MainActivity2.this,"state","sleep");
                            setData("sleep");
                            sharedPreferencesCustom.saveData(MainActivity2.this,"message",message.getText().toString());
                            createNotification(MainActivity2.this,status.getText().toString());
                            setAudioTo(status.getText().toString());
                            break;

                        case R.id.available:
                            //checking user opt for available message or not
                            if(sharedPreferencesCustom.loadSendMessageAvailable(MainActivity2.this)){
                                sharedPreferencesCustom.saveData(MainActivity2.this,"state","available");
                                setData("available");
                                sharedPreferencesCustom.saveData(MainActivity2.this,"message",message.getText().toString());
                                createNotification(MainActivity2.this,status.getText().toString());
                                setAudioTo(status.getText().toString());
                            }else{
                                sharedPreferencesCustom.saveData(MainActivity2.this,"state","available");
                                setData("available");
                                sharedPreferencesCustom.saveData(MainActivity2.this,"message",message.getText().toString());
                                createNotification(MainActivity2.this,status.getText().toString());
                                setAudioTo(status.getText().toString());
                            }
                            break;
                    }
                }
            });
        }else{
            //disable the radio buttons
        }



    }
}