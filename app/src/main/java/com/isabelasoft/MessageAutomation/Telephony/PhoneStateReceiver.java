package com.isabelasoft.MessageAutomation.Telephony;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.isabelasoft.MessageAutomation.Automation.DatabaseService;
import com.isabelasoft.MessageAutomation.Automation.SendMessage;
import com.isabelasoft.MessageAutomation.Database.DatabaseClient;
import com.isabelasoft.MessageAutomation.Database.MsgAutomationDB;
import com.isabelasoft.MessageAutomation.Model.ReceiverNumberModel;



public class PhoneStateReceiver extends BroadcastReceiver {

    private ReceiverNumberModel receiverNumberModel = new ReceiverNumberModel();
    private SendMessage sendMessage = new SendMessage();
    private String receiveNumber;
    private int count = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        final String TAG = "Receiver Number";
        DatabaseService databaseService = new DatabaseService(context);

        try {
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            receiverNumberModel.setIncomingNumber(intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER));
            receiveNumber = receiverNumberModel.getIncomingNumber();
            if (state.equals(TelephonyManager.EXTRA_STATE_RINGING) && receiveNumber != null) {
                Toast.makeText(context, "Ringing State Number is - " + receiveNumber, Toast.LENGTH_SHORT).show();
                savePhoneNo(receiveNumber,context);

                String stateStatus = databaseService.findState();
                String meetingMsgBody = "Hey I am a Bot Here, Seems like Sagnik is in a Meeting. He will call you after the meeting. Thank you";
                String slippingMsgBody = "Hey I am a Bot Here, Seems like Sagnik is away for the moment, He will call you once he is available.Thank you";

                Log.i("database state", "onReceive: "+stateStatus);

                if (stateStatus == null){
                    Log.i("State Info", "State Status is Null");
                }
                else if(stateStatus.equals("I am Available")){
                    Log.i("State Info", "State Status is ="+stateStatus);
                    System.out.println("DONT SEND");
//            System.exit(0);
                }
                else if(stateStatus.equals("I am sleeping") && count==0){
//            Log.i("State Info", "State(I am sleeping) Status is ="+stateStatus);
                    count++;
                    sendMessage.sendSMS(receiverNumberModel.getIncomingNumber(),slippingMsgBody,context);
                }
                else if(stateStatus.equals("In A Meeting") && count==0){
//            Log.i("State Info", "State(I am Metting )Status is ="+stateStatus);
                    count++;
                    sendMessage.sendSMS(receiverNumberModel.getIncomingNumber(),meetingMsgBody,context);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    void savePhoneNo(String receiveNumber,Context context){
          class SavePhoneNo extends AsyncTask<Void,Void,Void>{
              @Override
              protected Void doInBackground(Void... voids) {
                  MsgAutomationDB dbClient;
                  dbClient = DatabaseClient.getInstance(context.getApplicationContext()).getAppDatabase();
                  String phoneNo = dbClient.receiverNumberDao().findIncomingNumber();
//                  Log.d("Phone no", "doInBackground: save Phone no="+phoneNo);

                  if (phoneNo == null){
                      dbClient.receiverNumberDao().insertPhoneNo(receiveNumber);
                  }else{
                      dbClient.receiverNumberDao().updatePhoneNo(receiveNumber);
                  }

                  return null;
              }
          }
          SavePhoneNo sp = new SavePhoneNo();
          sp.execute();
    }

}
