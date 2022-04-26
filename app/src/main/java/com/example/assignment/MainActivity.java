package com.example.assignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.work.Data;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import com.example.assignment.databinding.ActivityMainBinding;
import com.example.assignment.entity.Student;
import com.example.assignment.viewModel.StudentViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    // private StudentViewModel studentViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        List<String> list = new ArrayList<String>();
        list.add("Toy Story");
        list.add("Up");
        list.add("Shrek");

        // studentViewModel = new ViewModelProvider(this).get(StudentViewModel.class);

        final ArrayAdapter<String> spinnerAdapter = new
                ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        binding.movieSpinner.setAdapter(spinnerAdapter);

        binding.addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String newMovie = binding.editText.getText().toString();
                spinnerAdapter.add(newMovie);
                spinnerAdapter.notifyDataSetChanged();
                binding.movieSpinner.setSelection(spinnerAdapter.getPosition(newMovie));
            }
        });
        binding.clearButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //binding.editText.getText().toString();
                binding.editText.setText("");
            }
        });

        binding.signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

        binding.jumpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ThirdActivity.class));
            }
        });

//        List<Student> studentList = studentViewModel.getAllStudentsInList();
//        // {"001":{"firstName": "frank", "lastName": "Zhang", "id": "001"}}
//        Map<String, Student> studentMap = new HashMap<>();
//        for (Student student: studentList)
//        {
//            studentMap.put(student.getStudentId(), student);
//        }
//        Gson gson = new Gson();
//        String jsonStr = gson.toJson(studentMap);
//
//        // Transform jsonStr into "Data" type
//        Data.Builder uploadPlaceBuilder = new Data.Builder();
//        Map<String, Object> placeMap = new HashMap<>();
//        placeMap.put("StudentJsonStr", jsonStr);
//        uploadPlaceBuilder.putAll(placeMap);
//        Data placeInfoInputData = uploadPlaceBuilder.build();
//
//        // transfer the data to work manager
//        WorkRequest saveRequest =
//                new PeriodicWorkRequest.Builder(UploadWorker.class,
//                        1, TimeUnit.DAYS,
//                        120,TimeUnit.MINUTES)
//                        .setInputData(placeInfoInputData)
//                        .build();
//        WorkManager.getInstance(this).enqueue(saveRequest);

        // https://api.openweathermap.org/data/2.5/weather?lat=-37.813629&lon=144.963058&appid=80273ca2896861a72eca02c8f231e796
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherApiInterface weatherApiInterface = retrofit.create(WeatherApiInterface.class);

        Call<Root> call = weatherApiInterface.getWeather();

        call.enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                Root root = response.body();
                double temp =  root.getMain().getTemp() - 273.15;
                binding.tempTextView.setText("Clayton: " + String.valueOf((int)temp) + "°C");
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });

    }

}