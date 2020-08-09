package com.isabelasoft.MessageAutomation.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.isabelasoft.MessageAutomation.Model.StateModel;

@Dao
public interface StateDao {
    @Query(value = "SELECT State from StateModel WHERE id=1")
    String findState();
    @Query(value = "INSERT INTO StateModel (State) VALUES (:state)")
    void insetState(String state);
    @Query(value = "UPDATE StateModel SET State=:state WHERE id=1")
    void updateState(String state);
}
