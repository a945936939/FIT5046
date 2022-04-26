package com.example.assignment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assignment.databinding.ActivityNewStudentBinding;
import com.example.assignment.databinding.ActivityThirdBinding;

public class NewStudentActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.android.studentlistsql.REPLY";

    private ActivityNewStudentBinding binding;

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        binding = ActivityNewStudentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(binding.editStudentId.getText()) ||
                        TextUtils.isEmpty(binding.editStudentFirstName.getText()) ||
                        TextUtils.isEmpty(binding.editStudentLastName.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                }
                else{
                    String studentId = binding.editStudentId.getText().toString();
                    String studentFirstName = binding.editStudentFirstName.getText().toString();
                    String studentLastName = binding.editStudentLastName.getText().toString();
                    replyIntent.putExtra("studentId", studentId);
                    replyIntent.putExtra("studentFirstName", studentFirstName);
                    replyIntent.putExtra("studentLastName", studentLastName);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }
}
