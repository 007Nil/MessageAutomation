package com.example.messageautomation.Telephony;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.example.messageautomation.SendMessage.SendMessage;
import com.example.messageautomation.SharedPreferences.SharedPreferencesCustom;

public class PhoneStateReceiver extends BroadcastReceiver {

    private String callState;
    private String incomingNumber;
    private SharedPreferencesCustom sharedPreferencesCustom = new SharedPreferencesCustom();
    private String currentState;
    private String message;
    private SendMessage sendMessage = new SendMessage();
    private static boolean callRinging=false;
    private static boolean callReceived =false;


    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            callState = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            if (callState.equals(TelephonyManager.EXTRA_STATE_RINGING) && incomingNumber != null) {
                callRinging = true;
                currentState = sharedPreferencesCustom.loadData(context);
                message = sharedPreferencesCustom.loadMsg(context);
                if (!currentState.equals("available")){
                    sendMessage.sendSMS(incomingNumber,message,context);
                    callRinging = false;
                }
            }

            if(callState.equals(TelephonyManager.EXTRA_STATE_OFFHOOK) && incomingNumber != null){
                callReceived = true;
            }

            if(callState.equals(TelephonyManager.EXTRA_STATE_IDLE) && incomingNumber != null){
                message = sharedPreferencesCustom.loadMsg(context);
                if(callRinging && !callReceived){
                    sendMessage.sendSMS(incomingNumber,message,context);
                }
                callRinging = false;
                callReceived = false;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }


    }


}