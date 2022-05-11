package com.example.assignment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
<<<<<<< HEAD
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
=======
>>>>>>> 9efbf723ec993b23c09505640c018c6842462d7d
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

import com.applandeo.materialcalendarview.EventDay;
import com.example.assignment.databinding.ActivityMainBinding;
<<<<<<< HEAD
import com.example.assignment.entity.Plan;
import com.example.assignment.fragment.HomeFragment;
import com.example.assignment.viewModel.PlanViewModel;
=======
>>>>>>> 9efbf723ec993b23c09505640c018c6842462d7d
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
//        List<String> dates = new ArrayList<String>();
//        List<String> dd = new ArrayList<String>();
//        List<String> mm = new ArrayList<String>();
//        List<String> yyyy = new ArrayList<String>();
//        List<Calendar> calendars = new ArrayList<>();
//
//
//        PlanViewModel planViewModel;
//        planViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(PlanViewModel.class);
//        planViewModel.getAllPlans().observe(this, new Observer<List<Plan>>() {
//            @RequiresApi(api = Build.VERSION_CODES.O)
//            @Override
//            public void onChanged(@Nullable final List<Plan> plans) {
//                for (Plan temp : plans) {
//                    DateFormat formatter = new SimpleDateFormat("dd-mm-yy");
//                    dates.add(temp.planDate);
//
//                }
////                for (String days : dates){
////                    String [] parts = days.split("-");
////                    dd.add(parts[0]);
////                    mm.add(parts[1]);
////                    yyyy.add(parts[2]);
////                }
////                for(int i = 0; i < dd.size()-1; i++){
////                    int dds = Integer. parseInt(dd.get(0));
////                    int mms = Integer. parseInt(mm.get(0));
////                    int yys = Integer. parseInt(yyyy.get(0));
////                    Calendar calendar = Calendar.getInstance();
////                    calendar.set(yys,mms,dds);
////                    calendars.add(calendar);
////                }
//
//                Calendar calendar = Calendar.getInstance();
//                calendar.set(2021, 5, 13);
//                calendars.add(calendar);
//                binding.calendarView.setHighlightedDays(calendars);
//            }
//        });

        /**List<String> list = new ArrayList<String>();
        list.add("Toy Story");
        list.add("Up");
        list.add("Shrek");*/
        setSupportActionBar(binding.appBar.toolbar);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home_fragment,
                R.id.nav_add_fragment,
                R.id.nav_view_fragment,
                R.id.ThirdActivity,
                R.id.PlanListActivity,
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

        // https://api.openweathermap.org/data/2.5/weather?lat=-37.813629&lon=144.963058&appid=80273ca2896861a72eca02c8f231e796
        /**Retrofit retrofit = new Retrofit.Builder()
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
        });*/
    }

}