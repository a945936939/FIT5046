package com.example.assignment.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.assignment.entity.User;

import java.util.List;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(User user);

    @Query("DELETE FROM user_table")
    void deleteAll();

    @Query("SELECT * FROM user_table ORDER BY userId ASC")
    List<User> getAlphabetizedUsers();

    @Query("SELECT * FROM user_table ORDER BY userId ASC")
    LiveData<List<User>> getAlphabetizedUserInLiveData();

    @Delete
    void delete(User user);

    @Update
    void updateUser(User user);

}
