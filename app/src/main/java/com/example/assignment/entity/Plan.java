package com.example.assignment.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "plan_table")
public class Plan {
    @PrimaryKey(autoGenerate = true)
    public int uid;
    @ColumnInfo(name = "plan_name")
    @NonNull
    public String planName;
    @ColumnInfo(name = "plan_date")
    @NonNull
    public String planDate;
    @ColumnInfo(name = "plan_content")
    @NonNull
    public String planContent;

    public Plan(@NonNull String planName, @NonNull String planDate, String planContent) {
        this.planName=planName;
        this.planDate=planDate;
        this.planContent = planContent;
    }
}