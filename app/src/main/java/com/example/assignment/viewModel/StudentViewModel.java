package com.example.assignment.viewModel;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.assignment.entity.Student;
import com.example.assignment.repository.StudentRepository;

import java.util.List;

public class StudentViewModel extends AndroidViewModel {

    private final StudentRepository sRepository;
    private final LiveData<List<Student>> allStudents;
    private final List<Student> allStudentsInList;

    public StudentViewModel (Application application) {
        super(application);
        sRepository = new StudentRepository(application);
        allStudents = sRepository.getAllStudents();
        allStudentsInList = sRepository.getStudentsInList();
    }

    public List<Student> getAllStudentsInList() {
        return allStudentsInList;
    }

    public LiveData<List<Student>> getAllStudents() {
        return allStudents;
    }

    public void insert(Student student) {
        sRepository.insert(student);
    }

    public void deleteAll() {
        sRepository.deleteAll();
    }

    public void update(Student student) {
        sRepository.updateStudent(student);
    }
}
