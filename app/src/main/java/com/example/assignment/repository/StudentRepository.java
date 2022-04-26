package com.example.assignment.repository;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import com.example.assignment.dao.StudentDao;
import com.example.assignment.database.StudentDatabase;
import com.example.assignment.entity.Student;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class StudentRepository {

    private StudentDao studentDao;
    private LiveData<List<Student>> allStudents;
    private List<Student> allstudentsInList;

    public StudentRepository(Application application){
        StudentDatabase db = StudentDatabase.getInstance(application);
        studentDao =db.studentDao();
        allStudents = studentDao.getAlphabetizedStudentInLiveData();
        allstudentsInList = studentDao.getAlphabetizedStudents();
    }

    public List<Student> getStudentsInList() {
        return allstudentsInList;
    }

    // Room executes this query on a separate thread
    // Update data in real time
    public LiveData<List<Student>> getAllStudents() {
        return allStudents;
    }

    public void insert(final Student student){
        StudentDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                studentDao.insert(student);
            }
        });
    }

    public void deleteAll(){
        StudentDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                studentDao.deleteAll();
            }
        });
    }

    public void delete(final Student student){
        StudentDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                studentDao.delete(student);
            }
        });
    }

    public void updateStudent(final Student student){
        StudentDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                studentDao.updateStudent(student);
            }
        });
    }

}
