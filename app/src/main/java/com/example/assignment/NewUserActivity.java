package com.example.assignment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assignment.databinding.ActivityNewUserBinding;
import com.example.assignment.databinding.ActivityThirdBinding;

public class NewUserActivity extends AppCompatActivity {

    private ActivityNewUserBinding binding;

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        binding = ActivityNewUserBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(binding.editUserId.getText()) ||
                        TextUtils.isEmpty(binding.editUserFirstName.getText()) ||
                        TextUtils.isEmpty(binding.editUserLastName.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                }
                else{
                    String userId = binding.editUserId.getText().toString();
                    String userFirstName = binding.editUserFirstName.getText().toString();
                    String userLastName = binding.editUserLastName.getText().toString();
                    replyIntent.putExtra("userId", userId);
                    replyIntent.putExtra("userFirstName", userFirstName);
                    replyIntent.putExtra("userLastName", userLastName);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });

        binding.clearButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                binding.editUserId.setText("");
                binding.editUserFirstName.setText("");
                binding.editUserLastName.setText("");
            }
        });
    }
}
