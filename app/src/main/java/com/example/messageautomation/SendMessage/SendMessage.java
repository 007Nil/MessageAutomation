package com.example.messageautomation.SendMessage;

import android.content.Context;
import android.telephony.SmsManager;
import android.widget.Toast;

import java.util.ArrayList;

public class SendMessage {
    private static final String TAG = "SendMessage";

    public void sendSMS(String phoneNo, String msg, Context context) {

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null,msg, null, null);
            Toast.makeText(context.getApplicationContext(), "Message Sent",Toast.LENGTH_LONG).show();
            System.out.println("Message Sent :"+msg);
        } catch (Exception ex) {
            Toast.makeText(context.getApplicationContext(), ex.getMessage(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }

}