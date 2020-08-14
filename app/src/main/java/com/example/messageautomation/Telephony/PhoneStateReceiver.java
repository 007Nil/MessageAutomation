package com.example.messageautomation.Telephony;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
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
                /*currentState = sharedPreferencesCustom.loadData(context);
                message = sharedPreferencesCustom.loadMsg(context);
                if (!currentState.equals("available")){
                    sendMessage.sendSMS(incomingNumber,message,context);
                    callRinging = false;
                    state = currentState;
                }*/
            }

            if(callState.equals(TelephonyManager.EXTRA_STATE_OFFHOOK) && incomingNumber != null){
                callReceived = true;
            }

            if(callState.equals(TelephonyManager.EXTRA_STATE_IDLE) && incomingNumber != null){
                currentState = sharedPreferencesCustom.loadData(context);
                message = sharedPreferencesCustom.loadMsg(context);
                if(callRinging && !callReceived && currentState.equals("available")){
                    if(sharedPreferencesCustom.loadSendMessageAvailable(context)){
                        sendMessage.sendSMS(incomingNumber,message,context);
                        switch (getResultCode()){
                            case Activity.RESULT_OK:
                                Toast.makeText(context, "Message Sent",Toast.LENGTH_LONG).show();
                                break;

                            case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                                Toast.makeText(context,"Failed to send message",Toast.LENGTH_LONG).show();
                                break;

                            case SmsManager.RESULT_ERROR_NO_SERVICE:
                                Toast.makeText(context,"No service available",Toast.LENGTH_LONG).show();
                                break;
                            default:
                                Toast.makeText(context,"Error! No message is sent",Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(context,"No message is sent",Toast.LENGTH_LONG).show();
                    }
                }else if(callRinging && callReceived && !currentState.equals("available")){
                    Toast.makeText(context,"Since you received the call message will not be sent",Toast.LENGTH_LONG).show();
                }else if(callRinging && !callReceived){
                    sendMessage.sendSMS(incomingNumber,message,context);
                    switch (getResultCode()){
                        case Activity.RESULT_OK:
                            Toast.makeText(context, "Message Sent",Toast.LENGTH_LONG).show();
                            break;

                        case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                            Toast.makeText(context,"Failed to send message",Toast.LENGTH_LONG).show();
                            break;

                        case SmsManager.RESULT_ERROR_NO_SERVICE:
                            Toast.makeText(context,"No service available",Toast.LENGTH_LONG).show();
                            break;

                        default:
                            Toast.makeText(context,"Error! No message is sent",Toast.LENGTH_LONG).show();
                    }

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