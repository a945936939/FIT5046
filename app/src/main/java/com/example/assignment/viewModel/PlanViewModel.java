package com.example.assignment.viewModel;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.assignment.entity.Plan;
import com.example.assignment.repository.PlanRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class PlanViewModel extends AndroidViewModel {
    private PlanRepository cRepository;
    private LiveData<List<Plan>> allPlans;
    public PlanViewModel (Application application) {
        super(application);
        cRepository = new PlanRepository(application);
        allPlans = cRepository.getAllCustomers();
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public CompletableFuture<Plan> findByIDFuture(final int customerId){
        return cRepository.findByIDFuture(customerId);
    }
    public LiveData<List<Plan>> getAllCustomers() {
        return allPlans;
    }
    public void insert(Plan plan) {
        cRepository.insert(plan);
    }

    public void deleteAll() {
        cRepository.deleteAll();
    }
    public void update(Plan plan) {
        cRepository.updateCustomer(plan);
    }
}
