package com.isabelasoft.MessageAutomation.Automation;

import android.content.Context;

import com.isabelasoft.MessageAutomation.Database.DatabaseClient;
import com.isabelasoft.MessageAutomation.Database.MsgAutomationDB;

public class DatabaseService {

    private Context context;
    private MsgAutomationDB dbInstance;

    public DatabaseService(Context context) {
        this.context = context;
    }

    public String findState(){
        String state = null;
        dbInstance = DatabaseClient.getInstance(context.getApplicationContext()).getAppDatabase();
        state = dbInstance.stateDao().findState();
        return state;
    }

    public String findPhoneNo(){
        String phoneNo = null;
        dbInstance = DatabaseClient.getInstance(context.getApplicationContext()).getAppDatabase();
        phoneNo = dbInstance.receiverNumberDao().findIncomingNumber();
        return phoneNo;
    }
}
