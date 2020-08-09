package com.isabelasoft.MessageAutomation.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.isabelasoft.MessageAutomation.Model.ReceiverNumberModel;


@Dao
public interface ReceieveNumberDao {
    @Query(value = "SELECT IncomingNumber FROM ReceiverNumberModel WHERE ID=1")
    String findIncomingNumber();
    @Query(value = "INSERT INTO ReceiverNumberModel (incomingNumber) VALUES (:incomingNumber)")
    void insertPhoneNo(String incomingNumber);
    @Query(value = "UPDATE ReceiverNumberModel SET IncomingNumber=:incomingNumber WHERE id=1")
    void updatePhoneNo(String incomingNumber);
}
