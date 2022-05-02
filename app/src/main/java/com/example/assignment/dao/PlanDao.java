package com.example.assignment.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.assignment.entity.Plan;

import java.util.List;

@Dao
public interface PlanDao {
    @Query("SELECT * FROM plan_table ORDER BY plan_name ASC")
    LiveData<List<Plan>> getAll();
    @Query("SELECT * FROM plan_table WHERE uid = :PlanId LIMIT 1")
    Plan findByID(int PlanId);
    @Insert
    void insert(Plan plan);
    @Delete
    void delete(Plan plan);
    @Update
    void updateCustomer(Plan plan);
    @Query("DELETE FROM plan_table")
    void deleteAll();
}
