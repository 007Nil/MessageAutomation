package com.isabelasoft.MessageAutomation.Database;

import android.content.Context;

import androidx.room.Room;

public class DatabaseClient {

    private Context mCtx;
    private static DatabaseClient mInstance;

    private MsgAutomationDB appDatabase;

    private DatabaseClient(Context mCtx){
        this.mCtx = mCtx;

        appDatabase = Room.databaseBuilder(mCtx,MsgAutomationDB.class,"MsgAutomation").allowMainThreadQueries().build();
    }

    public static synchronized DatabaseClient getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new DatabaseClient(mCtx);
        }
        return mInstance;
    }

    public MsgAutomationDB getAppDatabase() {
        return appDatabase;
    }
}
