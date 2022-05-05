package com.example.assignment.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.assignment.dao.PlanDao;
import com.example.assignment.database.PlanDatabase;
import com.example.assignment.entity.Plan;

import java.util.List;

public class PlanRepository {
    private PlanDao planDao;
    private LiveData<List<Plan>> allPlans;
    private List<Plan> allPlansInList;


    public PlanRepository(Application application){
        PlanDatabase db = PlanDatabase.getInstance(application);
        planDao =db.planDao();
        allPlans= planDao.getAll();
        // allPlansInList = planDao.getAllPlansInList();
    }

    // Room executes this query on a separate thread
    public LiveData<List<Plan>> getAllPlans() {
        return allPlans;
    }

    public List<Plan> getAllPlansInList() {
        return allPlansInList;
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
                planDao.updatePlan(plan);
            }
        });
    }

//    @RequiresApi(api = Build.VERSION_CODES.N)
//    public CompletableFuture<Plan> findByIDFuture(final String planId) {
//        return CompletableFuture.supplyAsync(new Supplier<Plan>() {
//            @Override
//            public Plan get() {
//                return planDao.findByID(planId);
//            }
//        }, PlanDatabase.databaseWriteExecutor);
//    }
}
