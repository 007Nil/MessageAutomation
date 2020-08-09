package com.isabelasoft.MessageAutomation.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class ReceiverNumberModel implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    private Integer id;
    @ColumnInfo(name = "IncomingNumber")
    private String incomingNumber;

    public ReceiverNumberModel() {
    }

    @Ignore
    public ReceiverNumberModel(Integer id, String incomingNumber) {
        this.id = id;
        this.incomingNumber = incomingNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIncomingNumber() {
        return incomingNumber;
    }

    public void setIncomingNumber(String incomingNumber) {
        this.incomingNumber = incomingNumber;
    }
}
