package com.example.assignment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import com.example.assignment.databinding.ActivityAddplanBinding;
import com.example.assignment.databinding.ActivityPlanlistBinding;
import com.example.assignment.entity.Plan;
import com.example.assignment.viewModel.PlanViewModel;

import java.util.List;

public class PlanListActivity extends AppCompatActivity {
    private ActivityPlanlistBinding binding;
    private com.example.assignment.viewModel.PlanViewModel PlanViewModel;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlanlistBinding.inflate(getLayoutInflater());
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
                        binding.textViewRead.setText("All data: " + allCustomers);
                    }
                });
    binding.deleteButton.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            PlanViewModel.deleteAll();
            binding.textViewDelete.setText("All data was deleted");
        }
    });
        binding.AddPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PlanListActivity.this, AddPlanActivity.class));
            }
        });

}}
