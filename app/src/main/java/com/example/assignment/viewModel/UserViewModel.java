package com.example.assignment.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.assignment.entity.User;
import com.example.assignment.repository.UserRepository;

import java.util.List;

public class UserViewModel extends AndroidViewModel {

    private final UserRepository sRepository;
    private final LiveData<List<User>> allUsers;
    private final List<User> allUsersInList;

    public UserViewModel (Application application) {
        super(application);
        sRepository = new UserRepository(application);
        allUsers = sRepository.getAllUsers();
        allUsersInList = sRepository.getUsersInList();
    }

    public List<User> getAllUsersInList() {
        return allUsersInList;
    }

    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }

    public void insert(User user) {
        sRepository.insert(user);
    }

    public void delete(User user) {sRepository.delete(user);}

    public void deleteAll() {
        sRepository.deleteAll();
    }

    public void update(User user) {
        sRepository.updateUser(user);
    }
}
