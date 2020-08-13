package com.example.messageautomation.SharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

public class SharedPreferencesCustom extends AppCompatActivity {

    private static final String SHARED_PREFS = "sharedPrefs";

    public void saveData(Context context, String key, String text) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, text);
        editor.apply();
    }

    public void saveData(Context context, String key, boolean text) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, text);
        editor.apply();
    }

    public String loadData(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String text = sharedPreferences.getString("state", "available");
        return text;
    }

    public String loadUsername(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String text = sharedPreferences.getString("username", "username");
        return text;
    }

    public String loadMsg(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String text = sharedPreferences.getString("message", "Sorry the owner isn't available." +
                " Will get back to you as soon as available." +
                "\n\nMessage auto-sent from Message Automation.");
        return text;
    }

    public boolean loadSendMessageAvailable(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        return sharedPreferences.getBoolean("sendMessageAvailable",false);
    }

//    public boolean loadSendMessageAvailable(){
//        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
//        return sharedPreferences.getBoolean("sendMessageAvailable",false);
//    }
}
