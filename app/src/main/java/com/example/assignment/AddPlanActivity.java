package com.example.assignment;

import static androidx.fragment.app.FragmentManager.TAG;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.assignment.databinding.ActivityAddplanBinding;
import com.example.assignment.entity.Plan;
import com.example.assignment.viewModel.PlanViewModel;

import java.util.List;

import timber.log.Timber;

public class AddPlanActivity extends AppCompatActivity {
    private ActivityAddplanBinding binding;
    private PlanViewModel planViewModel;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddplanBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddPlanActivity.this, MainActivity.class));
            }
        });

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddPlanActivity.this, CalendarActivity.class));
            }
        });

        binding.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddPlanActivity.this, PlanListActivity.class));
            }
        });

        Intent intent = getIntent();
        planViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(PlanViewModel.class);
        planViewModel.getAllPlans().observe(this, new Observer<List<Plan>>() {
                    @Override
                    public void onChanged(@Nullable final List<Plan> plans) {
                        String allPlans = "";
                        for (Plan temp : plans) {
                            String planDetails = ("Plan ID: " + temp.uid + "\n" +
                                    "Plan Name: " + temp.planName + "\n" +
                                    "Plan Date: " + temp.planDate + "\n" +
                                    "Plan Date: "+ temp.planContent);
                            allPlans = allPlans +
                                    System.getProperty("line.separator") + planDetails+"/n";
                        }
                    }
                });

        binding.planDate.getEditText().setText(intent.getStringExtra("date"));

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Timber.tag(TAG).d("OnDateSet: mm/dd/yyy" + month + "/" + day + "/" + year);
                String date = day + "/"+ month + "/" + year;
                binding.planDate.getEditText().setText(date);
            }
        };

        binding.addPlanButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String name = binding.planName.getEditText().getText().toString();
                String date = binding.planDate.getEditText().getText().toString();
                String content = binding.planContent.getEditText().getText().toString();
                String email = binding.planEmail.getEditText().getText().toString();
                String location = binding.planLocation.getEditText().getText().toString();
                if (!name.isEmpty() && !date.isEmpty() && !content.isEmpty()) {
                    Plan plan = new Plan(name, date, content);
                    planViewModel.insert(plan);
                    Toast.makeText(
                            getApplicationContext(),
                            "The plan has been added.",
                            Toast.LENGTH_LONG).show();
                    startActivity(new Intent(AddPlanActivity.this, PlanListActivity.class));

                }



            }
        });

        binding.addCalendar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String name = binding.planName.getEditText().getText().toString();
                String content = binding.planContent.getEditText().getText().toString();
                String email = binding.planEmail.getEditText().getText().toString();
                String location = binding.planLocation.getEditText().getText().toString();
                Intent intent = new Intent(Intent.ACTION_INSERT);
                intent.setData(CalendarContract.Events.CONTENT_URI);
                intent.putExtra(CalendarContract.Events.TITLE, name);
                intent.putExtra(CalendarContract.Events.EVENT_LOCATION, location);
                intent.putExtra(CalendarContract.Events.DESCRIPTION, content);
                intent.putExtra(CalendarContract.Events.ALL_DAY, true);
                intent.putExtra(Intent.EXTRA_EMAIL, email);
                startActivity(intent);
            }
        });


        binding.clearButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                binding.planId.getEditText().setText("");
                binding.planName.getEditText().setText("");
                binding.planDate.getEditText().setText("");
                binding.planContent.getEditText().setText("");
            }
        });

    }
}
