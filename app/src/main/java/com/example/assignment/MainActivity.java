package com.example.assignment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;

import com.example.assignment.databinding.ActivityMainBinding;
import com.example.assignment.entity.Plan;
import com.example.assignment.fragment.HeartRateFragment;
import com.example.assignment.fragment.HomeFragment;
import com.example.assignment.fragment.KcalFragment;
import com.example.assignment.fragment.WalkActivityFragment;
import com.example.assignment.viewModel.PlanViewModel;
import com.google.firebase.auth.FirebaseAuth;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding  binding;
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setSupportActionBar(binding.appBar.toolbar);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.home_fragment,
                R.id.heart_rate_fragment,
                R.id.walk_activity_fragment,
                R.id.kcal_fragment,
                R.id.ThirdActivity,
                R.id.PlanListActivity,
                R.id.MapActivity,
                R.id.ChartActivity)
                //to display the Navigation button as a drawer symbol,not being shown as an Up button
                .setOpenableLayout(binding.drawerLayout)
                .build();
        FragmentManager fragmentManager= getSupportFragmentManager();
        NavHostFragment navHostFragment = (NavHostFragment)
                fragmentManager.findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        //Sets up a NavigationView for use with a NavController.
        NavigationUI.setupWithNavController(binding.navigationView, navController);
        //Sets up a Toolbar for use with a NavController.
        NavigationUI.setupWithNavController(binding.appBar.toolbar,navController, mAppBarConfiguration);


        binding.homeButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MainActivity.class));
            }
        });

        binding.signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

        binding.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
        // userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        /**final ArrayAdapter<String> spinnerAdapter = new
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
        });*/

        /**binding.signOutButton.setOnClickListener(new View.OnClickListener() {
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

        binding.navigationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, NavigationActivity.class));
            }
        });*/

        /**
         * List<User> userList = userViewModel.getAllUsersInList();
        // {"001":{"firstName": "frank", "lastName": "Zhang", "id": "001"}}
        Map<String, User> userMap = new HashMap<>();
        for (User user: userList)
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
         */
    }

    public void getHeartRateFragment(){
        FragmentManager fragmentmanager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentmanager.beginTransaction();
        HeartRateFragment heartRateFragment = new HeartRateFragment();
        fragmentTransaction.replace(R.id.nav_host_fragment, heartRateFragment);
        fragmentTransaction.commit();
    }
    public void getWalkActivityFragment(){
        FragmentManager fragmentmanager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentmanager.beginTransaction();
        WalkActivityFragment walkActivityFragment = new WalkActivityFragment();
        fragmentTransaction.replace(R.id.nav_host_fragment, walkActivityFragment);
        fragmentTransaction.commit();
    }
    public void getKcalFragment(){
        FragmentManager fragmentmanager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentmanager.beginTransaction();
        KcalFragment kcalFragment = new KcalFragment();
        fragmentTransaction.replace(R.id.nav_host_fragment, kcalFragment);
        fragmentTransaction.commit();
    }

}