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

    @NonNull
    @ColumnInfo(name = "plan_name")
    public String planName;

    @NonNull
    @ColumnInfo(name = "plan_date")
    public String planDate;

    @NonNull
    @ColumnInfo(name = "plan_content")
    public String planContent;

    public Plan(@NonNull String planName, @NonNull String planDate, String planContent) {
        this.planName=planName;
        this.planDate=planDate;
        this.planContent = planContent;
    }

    public int getUid() {
        return uid;
    }

    @NonNull
    public String getPlanName() {
        return planName;
    }

    @NonNull
    public String getPlanDate() {
        return planDate;
    }

    @NonNull
    public String getPlanContent() {
        return planContent;
    }
}