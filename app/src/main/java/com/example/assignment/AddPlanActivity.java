package com.example.assignment;

import static androidx.fragment.app.FragmentManager.TAG;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.assignment.databinding.ActivityAddplanBinding;
import com.example.assignment.databinding.ActivityMainBinding;
import com.example.assignment.entity.Plan;
import com.example.assignment.viewModel.PlanViewModel;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
        Intent intent = getIntent();
        planViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(PlanViewModel.class);
        planViewModel.getAllPlans().observe(this, new
                Observer<List<Plan>>() {
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
                month = month+1;
                Log.d(TAG,"OnDateSet: mm/dd/yyy"+month+"/"+day+"/"+year);
                String date = day + "/"+ month + "/" + year;
                binding.planDate.getEditText().setText(date);
            }
        };

        binding.addPlanButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String name = binding.planName.getEditText().getText().toString();
                String date = binding.planDate.getEditText().getText().toString();
                String content = binding.planContent.getEditText().getText().toString();

                if ((!name.isEmpty() && name != null) && (!date.isEmpty() &&
                        content != null) && (!content.isEmpty() && date != null)) {
                    Plan plan = new Plan(name, date, content);
                    planViewModel.insert(plan);
                    Toast.makeText(
                            getApplicationContext(),
                            "The plan has been added.",
                            Toast.LENGTH_LONG).show();
                    startActivity(new Intent(AddPlanActivity.this, PlanListActivity.class));
                    /**binding.textViewAdd.setText("Plan Added: " +"\n" +
                                                "Plan ID:" + plan.uid +
                                                "Plan Name:" + name +
                                                "\n"+"Plan Date:" + date +
                                                "\n" +"Plan content:"+ content);*/
                }
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
