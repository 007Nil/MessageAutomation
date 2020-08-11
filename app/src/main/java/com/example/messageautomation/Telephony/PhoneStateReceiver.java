package com.example.messageautomation.Telephony;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class PhoneStateReceiver extends BroadcastReceiver {

    private String callState;
    private String incomingNumber;

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            callState = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            if (callState.equals(TelephonyManager.EXTRA_STATE_RINGING) && incomingNumber != null){
                Toast.makeText(context, "Ringing State Number is - " + incomingNumber, Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }


    }
}