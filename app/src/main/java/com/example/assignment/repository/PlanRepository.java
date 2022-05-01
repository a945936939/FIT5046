package com.example.assignment.repository;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import com.example.assignment.dao.PlanDao;
import com.example.assignment.database.PlanDatabase;
import com.example.assignment.entity.Plan;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class PlanRepository {
    private PlanDao planDao;
    private LiveData<List<Plan>> allPlans;
    public PlanRepository(Application application){
        PlanDatabase db = PlanDatabase.getInstance(application);
        planDao =db.customerDao();
        allPlans= planDao.getAll();
    }
    // Room executes this query on a separate thread
    public LiveData<List<Plan>> getAllCustomers() {
        return allPlans;
    }
    public void insert(final Plan plan){
        PlanDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                planDao.insert(plan);
            }
        });
    }
    public void deleteAll(){
        PlanDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                planDao.deleteAll();
            }
        });
    }
    public void delete(final Plan plan){
        PlanDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                planDao.delete(plan);
            }
        });
    }
    public void updateCustomer(final Plan plan){
        PlanDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                planDao.updateCustomer(plan);
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public CompletableFuture<Plan> findByIDFuture(final int planId) {
        return CompletableFuture.supplyAsync(new Supplier<Plan>() {
            @Override
            public Plan get() {
                return planDao.findByID(planId);
            }
        }, PlanDatabase.databaseWriteExecutor);
    }
}
