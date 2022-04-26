package com.example.assignment;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.assignment.entity.Student;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;

public class UploadWorker extends Worker {
    public UploadWorker(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
    }

    @Override
    public Result doWork() {

        String studentJsonStr = getInputData().getString("StudentJsonStr");
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Student");
        // Transform string into Map type
        Gson gson = new Gson();
        Type studentMapType = new TypeToken<Map<String, Student>>() {}.getType();
        Map<String, Student> studentMap = gson.fromJson(studentJsonStr, studentMapType);
        // upload the data to firebase database
        databaseReference.setValue(studentMap);
        // indicate whether the work finished successfully with the result
        return Result.success();
    }
}

