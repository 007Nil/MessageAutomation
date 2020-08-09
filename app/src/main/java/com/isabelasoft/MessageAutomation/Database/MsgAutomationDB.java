package com.isabelasoft.MessageAutomation.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.RoomDatabase;


import com.isabelasoft.MessageAutomation.Dao.ReceieveNumberDao;
import com.isabelasoft.MessageAutomation.Dao.StateDao;
import com.isabelasoft.MessageAutomation.Model.ReceiverNumberModel;
import com.isabelasoft.MessageAutomation.Model.StateModel;

@Database(entities = {StateModel.class, ReceiverNumberModel.class},exportSchema = false,version = 1)
public abstract class MsgAutomationDB extends RoomDatabase {

    public abstract StateDao stateDao();
    public abstract ReceieveNumberDao receiverNumberDao();

}
