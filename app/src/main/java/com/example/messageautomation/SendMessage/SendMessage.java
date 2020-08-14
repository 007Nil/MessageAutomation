package com.example.messageautomation.SendMessage;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.SmsManager;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.example.messageautomation.SharedPreferences.SharedPreferencesCustom;

import java.util.ArrayList;
import java.util.List;

public class SendMessage {
    private static final String TAG = "SendMessage";
    private SharedPreferencesCustom sharedPreferencesCustom = new SharedPreferencesCustom();

    @SuppressLint("MissingPermission")
    public void sendSMS(String phoneNo, String msg, Context context) {
        String sim = sharedPreferencesCustom.loadSim(context);
        try {
            SmsManager smsManager = SmsManager.getDefault();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                SubscriptionManager localSubscriptionManager = SubscriptionManager.from(context);
                if (localSubscriptionManager.getActiveSubscriptionInfoCount() > 1) {
                    List localList = localSubscriptionManager.getActiveSubscriptionInfoList();

                    SubscriptionInfo simInfo1 = (SubscriptionInfo) localList.get(0);
                    SubscriptionInfo simInfo2 = (SubscriptionInfo) localList.get(1);

                    if(sim.equalsIgnoreCase("sim1")){
                        //SendSMS From SIM One
                        SmsManager.getSmsManagerForSubscriptionId(simInfo1.getSubscriptionId()).sendTextMessage(phoneNo, null, msg, null, null);
                    }else{
                        //SendSMS From SIM Two
                        SmsManager.getSmsManagerForSubscriptionId(simInfo2.getSubscriptionId()).sendTextMessage(phoneNo, null, msg, null, null);
                    }
                }
            }else{
                smsManager.sendTextMessage(phoneNo, null,msg, null, null);
            }
            //smsManager.sendTextMessage(phoneNo, null,msg, null, null);
            //Toast.makeText(context.getApplicationContext(), "Message Sent",Toast.LENGTH_LONG).show();
            //System.out.println("Message Sent :"+msg);
        } catch (Exception ex) {
            Toast.makeText(context.getApplicationContext(), ex.getMessage(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }

}
