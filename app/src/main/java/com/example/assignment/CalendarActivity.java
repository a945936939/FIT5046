package com.example.assignment;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.assignment.databinding.ActivityCalendarBinding;

import java.util.Calendar;

public class CalendarActivity extends AppCompatActivity {
    private ActivityCalendarBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCalendarBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CalendarActivity.this, MainActivity.class));
            }
        });

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CalendarActivity.this, PlanListActivity.class));
            }
        });

        binding.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CalendarActivity.this, AddPlanActivity.class));
            }
        });
        binding.calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int day) {
                        String Date = day + "-" + (month + 1) + "-" + year;
                        Intent intent = new Intent(CalendarActivity.this, AddPlanActivity.class);
                        intent.putExtra("date", Date);
                        startActivity(intent);
                    }
                });
    }
}