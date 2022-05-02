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
    private PlanViewModel PlanViewModel;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddplanBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        PlanViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(PlanViewModel.class);
        PlanViewModel.getAllCustomers().observe(this, new
                Observer<List<Plan>>() {
                    @Override
                    public void onChanged(@Nullable final List<Plan> plans) {
                        String allCustomers = "";
                        for (Plan temp : plans) {
                            String customerDetails = ("Plan Name:" + temp.planName + "\n"+"Plan Date:" + temp.planDate + "\n" +"Plan Date:"+ temp.planContent);
                            allCustomers = allCustomers +
                                    System.getProperty("line.separator") + customerDetails+"/n";
                        }
                    }
                });

        binding.addPlan.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(
                        AddPlanActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dateSetListener,
                        year,month,day
                );
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }

        });

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

        binding.planList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddPlanActivity.this, PlanListActivity.class));
            }
        });

        binding.addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String name = binding.planName.getEditText().getText().toString();
                String date = binding.planDate.getEditText().getText().toString();
                String content = binding.planContent.getEditText().getText().toString();

                if ((!name.isEmpty() && name != null) && (!date.isEmpty() &&
                        content != null) && (!content.isEmpty() && date != null)) {
                    Plan plan = new Plan(name, date, content);
                    PlanViewModel.insert(plan);
                    binding.textViewAdd.setText("Plan Added: " +"\n" +
                                                "Plan Name:" + name +
                                                "\n"+"Plan Date:" + date +
                                                "\n" +"Plan content:"+ content);
                }
            }
        });

        binding.clearButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                binding.planName.getEditText().setText("");
                binding.planDate.getEditText().setText("");
                binding.planContent.getEditText().setText("");
            }
        });

    }
}
