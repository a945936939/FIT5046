package com.example.assignment.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_table")
public class User {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "userId")
    private String userId;

    @NonNull
    @ColumnInfo(name = "firstName")
    private String firstName;

    @NonNull
    @ColumnInfo(name = "lastName")
    private String lastName;

    public User(@NonNull String userId, @NonNull String firstName, @NonNull String lastName){
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @NonNull
    public String getUserId(){
        return userId;
    }

    public void setUserId(@NonNull String userId){
        this.userId = userId;
    }

    @NonNull
    public String getFirstName(){
        return firstName;
    }

    public void setFirstName(@NonNull String firstName){
        this.firstName = firstName;
    }

    @NonNull
    public String getLastName(){
        return lastName;
    }

    public void setLastName(@NonNull String lastName){
        this.lastName = lastName;
    }
}
