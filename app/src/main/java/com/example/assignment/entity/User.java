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

    @NonNull
    @ColumnInfo(name = "gender")
    private String gender;

    @NonNull
    @ColumnInfo(name = "age")
    private int age;

    @NonNull
    @ColumnInfo(name = "yearOfBirth")
    private String yearOfBirth;

    @NonNull
    @ColumnInfo(name = "Height")
    private double Height;

    public User(@NonNull String userId, @NonNull String firstName, @NonNull String lastName, @NonNull String gender, int age, @NonNull String yearOfBirth, double Height) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.age = age;
        this.yearOfBirth = yearOfBirth;
        this.Height = Height;
    }

    @NonNull
    public String getGender() { return gender; }

    public int getAge() { return age; }

    @NonNull
    public String getYearOfBirth() { return yearOfBirth; }

    public double getHeight() { return Height; }

    @NonNull
    public String getUserId(){
        return userId;
    }

    @NonNull
    public String getFirstName(){
        return firstName;
    }

    @NonNull
    public String getLastName(){
        return lastName;
    }

}
