package com.s_and_r_funLabs.messageautomation.SendMessage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.telephony.SmsManager;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.widget.Toast;

import com.s_and_r_funLabs.messageautomation.SharedPreferences.SharedPreferencesCustom;

import java.util.List;

public class SendMessage {
    private static final String TAG = "SendMessage";
    private SubscriptionInfo simInfo;
    private List localList;
    private SharedPreferencesCustom sharedPreferencesCustom = new SharedPreferencesCustom();

    @SuppressLint("MissingPermission")
    public void sendSMS(String phoneNo, String msg, Context context) {
        String sim = sharedPreferencesCustom.loadSim(context);
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                SubscriptionManager localSubscriptionManager = SubscriptionManager.from(context);
                localList = localSubscriptionManager.getActiveSubscriptionInfoList();
                if (localSubscriptionManager.getActiveSubscriptionInfoCount() > 1) {
                    //localList = localSubscriptionManager.getActiveSubscriptionInfoList();

                    SubscriptionInfo simInfo1 = (SubscriptionInfo) localList.get(0);
                    SubscriptionInfo simInfo2 = (SubscriptionInfo) localList.get(1);

                    if(sim.equalsIgnoreCase("sim1")){
                        //SendSMS From SIM One
                        SmsManager.getSmsManagerForSubscriptionId(simInfo1.getSubscriptionId()).sendTextMessage(phoneNo, null, msg, null, null);
                    }else{
                        //SendSMS From SIM Two
                        SmsManager.getSmsManagerForSubscriptionId(simInfo2.getSubscriptionId()).sendTextMessage(phoneNo, null, msg, null, null);
                    }
                }else{
                    simInfo = (SubscriptionInfo) localList.get(0);
                    SmsManager.getSmsManagerForSubscriptionId(simInfo.getSubscriptionId()).sendTextMessage(phoneNo,null,msg,null,null);
                }
            }else{
                SmsManager smsManager = SmsManager.getDefault();
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
