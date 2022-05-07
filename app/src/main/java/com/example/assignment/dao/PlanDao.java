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


    @Query("SELECT * FROM plan_table ORDER BY uid ASC")
    LiveData<List<Plan>> getAll();

    @Query("SELECT * FROM plan_table ORDER BY uid ASC")
    List<Plan> getAllPlansInList();

    @Insert
    void insert(Plan plan);

    @Delete
    void delete(Plan plan);

    @Update
    void updatePlan(Plan plan);

    @Query("DELETE FROM plan_table")
    void deleteAll();
}
