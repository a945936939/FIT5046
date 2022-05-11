package com.example.assignment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import com.example.assignment.databinding.ActivityAddplanBinding;
import com.example.assignment.databinding.ActivityPlanlistBinding;
import com.example.assignment.entity.Plan;
import com.example.assignment.viewModel.PlanViewModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlanListActivity extends AppCompatActivity {
    private ActivityPlanlistBinding binding;
    private PlanViewModel planViewModel;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlanlistBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        planViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(PlanViewModel.class);
        planViewModel.getAllPlans().observe(this, new Observer<List<Plan>>() {
            @Override
            public void onChanged(@Nullable final List<Plan> plans) {
                String allPlans = "";
                for (Plan temp : plans) {
                    String planDetails = ("Plan ID: " + temp.uid + "\n"+
                            "Plan Name: " + temp.planName + "\n" +
                            "Plan Date: " + temp.planDate + "\n" +
                            "Plan Details: "+ temp.planContent);
                    allPlans += System.getProperty("line.separator") + planDetails + "\n";
                }

                binding.textViewRead.setText(allPlans);
            }
        });

        binding.addPlanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PlanListActivity.this, Calendar.class));
            }
        });
        
        binding.uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = firebaseDatabase.getReference("Plan");
                // get current all plans
                LiveData<List<Plan>> planList = planViewModel.getAllPlans();
                databaseReference.setValue(planList);
                Toast.makeText(
                        getApplicationContext(),
                        "All plan has been uploaded.",
                        Toast.LENGTH_LONG).show();
            }
        });

        binding.homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PlanListActivity.this, MainActivity.class));
            }
        });

        binding.deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                planViewModel.deleteAll();
                Toast.makeText(
                        getApplicationContext(),
                        "All data was deleted.",
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
