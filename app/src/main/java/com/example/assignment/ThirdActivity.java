package com.example.assignment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Data;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.example.assignment.databinding.ActivityThirdBinding;
import com.example.assignment.entity.Student;
import com.example.assignment.viewModel.StudentViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ThirdActivity extends AppCompatActivity {

    private ActivityThirdBinding binding;
    private StudentViewModel studentViewModel;
    public static final int NEW_STUDENT_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityThirdBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        studentViewModel = new ViewModelProvider(this).get(StudentViewModel.class);

        final StudentListAdapter adapter = new StudentListAdapter(new StudentListAdapter.StudentDiff());
        binding.recyclerview.setAdapter(adapter);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this));

        // Update the cached copy of the students in the adapter.
        studentViewModel.getAllStudents().observe(this, adapter::submitList);

        binding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ThirdActivity.this, NewStudentActivity.class);
                startActivityForResult(intent, NEW_STUDENT_ACTIVITY_REQUEST_CODE);
            }
        });

        binding.uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = firebaseDatabase.getReference("Student");
                // get current all students
                List<Student> studentList = studentViewModel.getAllStudentsInList();

                // Map {001: {student1}, 002: {student2}}
                Map<String, Student> studentMap = new HashMap<>();
                for (Student student: studentList)
                {
                    studentMap.put(student.getStudentId(), student);
                }
                databaseReference.setValue(studentMap);
            }
        });

        List<Student> studentList = studentViewModel.getAllStudentsInList();
        // {"001":{"firstName": "frank", "lastName": "Zhang", "id": "001"}}
        Map<String, Student> studentMap = new HashMap<>();
        for (Student student: studentList)
        {
            studentMap.put(student.getStudentId(), student);
        }
        Gson gson = new Gson();
        String jsonStr = gson.toJson(studentMap);

        // Transform jsonStr into "Data" type
        Data.Builder uploadPlaceBuilder = new Data.Builder();
        Map<String, Object> placeMap = new HashMap<>();
        placeMap.put("StudentJsonStr", jsonStr);
        uploadPlaceBuilder.putAll(placeMap);
        Data placeInfoInputData = uploadPlaceBuilder.build();

        // transfer the data to work manager
        WorkRequest saveRequest =
                new PeriodicWorkRequest.Builder(UploadWorker.class,
                        1, TimeUnit.DAYS,
                        120,TimeUnit.MINUTES)
                        .setInputData(placeInfoInputData)
                        .build();
        WorkManager.getInstance(this).enqueue(saveRequest);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_STUDENT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Student student = new Student(data.getStringExtra("studentId"), data.getStringExtra("studentFirstName"),
                    data.getStringExtra("studentLastName"));
            studentViewModel.insert(student);
        }
        else{
            Toast.makeText(
                    getApplicationContext(),
                    "Student ID not saved because it is empty.",
                    Toast.LENGTH_LONG).show();
        }
    }
}
