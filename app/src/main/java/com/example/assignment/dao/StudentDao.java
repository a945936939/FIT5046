package com.example.assignment.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.assignment.entity.Student;

import java.util.List;

@Dao
public interface StudentDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Student student);

    @Query("DELETE FROM student_table")
    void deleteAll();

    @Query("SELECT * FROM student_table ORDER BY studentId ASC")
    List<Student> getAlphabetizedStudents();

    @Query("SELECT * FROM student_table ORDER BY studentId ASC")
    LiveData<List<Student>> getAlphabetizedStudentInLiveData();

    @Delete
    void delete(Student student);

    @Update
    void updateStudent(Student student);

}
