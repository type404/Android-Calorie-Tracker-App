package com.example.calorietracker;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.sql.Time;

@Entity
public class StepsTable {
    @PrimaryKey(autoGenerate = true)
    public int sid;
    @ColumnInfo(name = "time")
    public String time;
    @ColumnInfo(name = "steps_Taken")
    public Integer steps;
    public StepsTable(String time, Integer steps) {
        this.time = time;
        this.steps = steps;
    }

    public int getId() {
        return sid;
    }

    public String getTime() {
        return time;
    }
    public Integer getSteps() {
        return steps;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public void setSteps(Integer steps) {
        this.steps = steps;
    }
}
