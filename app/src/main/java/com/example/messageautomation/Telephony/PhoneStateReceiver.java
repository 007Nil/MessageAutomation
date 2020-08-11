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

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            callState = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            if (callState.equals(TelephonyManager.EXTRA_STATE_RINGING) && incomingNumber != null){
                Toast.makeText(context, "Ringing State Number is - " + incomingNumber, Toast.LENGTH_SHORT).show();
//                System.out.println(sharedPreferencesCustom.loadData(context));
//                System.out.println(sharedPreferencesCustom.loadMsg(context));
                currentState = sharedPreferencesCustom.loadData(context);
                message = sharedPreferencesCustom.loadMsg(context);

                if (!currentState.equals("available")){
                    sendMessage.sendSMS(incomingNumber,message,context);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }


    }
}