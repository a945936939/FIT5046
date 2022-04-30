package com.example.assignment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.work.Data;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.example.assignment.databinding.ActivityThirdBinding;
import com.example.assignment.entity.User;
import com.example.assignment.viewModel.UserViewModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ThirdActivity extends AppCompatActivity {

    private ActivityThirdBinding binding;
    private UserViewModel userViewModel;
    private List<User> users;
    public static final int NEW_STUDENT_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityThirdBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        UserListAdapter adapter = new UserListAdapter(new UserListAdapter.UserDiff());
        binding.recyclerview.setAdapter(adapter);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this));

        // Update the cached copy of the users in the adapter.
        userViewModel.getAllUsers().observe(this, adapter::submitList);

        binding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ThirdActivity.this, NewUserActivity.class);
                startActivityForResult(intent, NEW_STUDENT_ACTIVITY_REQUEST_CODE);
            }
        });

        binding.uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = firebaseDatabase.getReference("User");
                // get current all users
                List<User> userList = userViewModel.getAllUsersInList();

                // Map {001: {user1}, 002: {user2}}
                Map<String, User> userMap = new HashMap<>();
                for (User user : userList)
                {
                    userMap.put(user.getUserId(), user);
                }
                databaseReference.setValue(userMap);
            }
        });

        List<User> userList = userViewModel.getAllUsersInList();
        // {"001":{"firstName": "frank", "lastName": "Zhang", "id": "001"}}
        Map<String, User> userMap = new HashMap<>();
        for (User user : userList)
        {
            userMap.put(user.getUserId(), user);
        }
        Gson gson = new Gson();
        String jsonStr = gson.toJson(userMap);

        // Transform jsonStr into "Data" type
        Data.Builder uploadPlaceBuilder = new Data.Builder();
        Map<String, Object> placeMap = new HashMap<>();
        placeMap.put("UserJsonStr", jsonStr);
        uploadPlaceBuilder.putAll(placeMap);
        Data placeInfoInputData = uploadPlaceBuilder.build();

        // transfer the data to work manager
        WorkRequest saveRequest =
                new PeriodicWorkRequest.Builder(UploadWorker.class,
                        1, TimeUnit.DAYS,
                        120,TimeUnit.MINUTES)
                        .setInputData(placeInfoInputData)
                        .build();
        WorkManager.getInstance(this).enqueue(saveRequest);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_STUDENT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            User user = new User(data.getStringExtra("userId"), data.getStringExtra("userFirstName"),
                    data.getStringExtra("userLastName"));
            userViewModel.insert(user);
        }
        else{
            Toast.makeText(
                    getApplicationContext(),
                    "User ID not saved because it is empty.",
                    Toast.LENGTH_LONG).show();
        }
    }
}
