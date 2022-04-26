package com.example.assignment.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "student_table")
public class Student {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "studentId")
    private String studentId;

    @NonNull
    @ColumnInfo(name = "firstName")
    private String firstName;

    @NonNull
    @ColumnInfo(name = "lastName")
    private String lastName;

    public Student(@NonNull String studentId, @NonNull String firstName, @NonNull String lastName){
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @NonNull
    public String getStudentId(){
        return studentId;
    }

    public void setStudentId(@NonNull String studentId){
        this.studentId = studentId;
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
