package com.isabelasoft.MessageAutomation.Automation;

import android.content.Context;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import com.isabelasoft.MessageAutomation.Database.MsgAutomationDB;

public class SendMessage {

    private static final String TAG = "SendMessage";

    public void sendSMS(String phoneNo, String msg, Context context) {
        Log.i(TAG, "sendSMS: "+phoneNo);
        Log.i(TAG, "sendSMS: "+msg);
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, msg, null, null);
            Toast.makeText(context.getApplicationContext(), "Message Sent",
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(context.getApplicationContext(),ex.getMessage().toString(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }


}
