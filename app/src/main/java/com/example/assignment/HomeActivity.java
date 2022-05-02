package com.example.assignment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.assignment.Root;
import com.example.assignment.databinding.ActivityHomeBinding;
import com.example.assignment.viewModel.SharedViewModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity {
    private SharedViewModel model;
    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            }
        });

        binding.jumpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, ThirdActivity.class));
            }
        });

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
                double temp = root.getTemp() - 273.15;
                binding.tempTextView.setText("Clayton: " + String.valueOf((int) temp) + "°C");
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });

    }
}