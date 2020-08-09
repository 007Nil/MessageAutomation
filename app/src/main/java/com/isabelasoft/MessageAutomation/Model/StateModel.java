package com.isabelasoft.MessageAutomation.Model;


import androidx.room.ColumnInfo;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.Entity;

import java.io.Serializable;

@Entity(tableName = "StateModel")
public class StateModel implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    private Integer id;
    @ColumnInfo(name = "State")
    private String state;

    public StateModel() {
        super();
    }
    @Ignore
    public StateModel(Integer id, String state) {
        this.id = id;
        this.state = state;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "StateModel{" +
                "id=" + id +
                ", state='" + state + '\'' +
                '}';
    }
}
