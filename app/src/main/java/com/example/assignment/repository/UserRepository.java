package com.example.assignment.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.assignment.dao.UserDao;
import com.example.assignment.database.UserDatabase;
import com.example.assignment.entity.User;

import java.util.List;

public class UserRepository {

    private UserDao userDao;
    private LiveData<List<User>> allUsers;
    private List<User> allusersInList;

    public UserRepository(Application application){
        UserDatabase db = UserDatabase.getInstance(application);
        userDao =db.userDao();
        allUsers = userDao.getAlphabetizedUserInLiveData();
        allusersInList = userDao.getAlphabetizedUsers();
    }

    public List<User> getUsersInList() {
        return allusersInList;
    }

    // Room executes this query on a separate thread
    // Update data in real time
    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }

    public void insert(final User user){
        UserDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                userDao.insert(user);
            }
        });
    }

    public void deleteAll(){
        UserDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                userDao.deleteAll();
            }
        });
    }

    public void delete(final User user){
        UserDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                userDao.delete(user);
            }
        });
    }

    public void updateUser(final User user){
        UserDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                userDao.updateUser(user);
            }
        });
    }

}
