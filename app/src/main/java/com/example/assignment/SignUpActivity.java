package com.example.assignment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.assignment.databinding.ActivitySignupBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class SignUpActivity extends AppCompatActivity {
    private ActivitySignupBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        List<String> list = new ArrayList<String>();
        list.add("Male");
        list.add("Female");

        final ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this , android.R.layout.simple_spinner_item, list);
        binding.spinner.setAdapter(spinnerAdapter);

        mAuth = FirebaseAuth.getInstance();

        binding.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        binding.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validationSignUp();
            }
        });
    }



    private void validationSignUp(){

        boolean isValidEmail = false;
        binding.emailAddressInput.getText();
        // empty
        if (binding.emailAddressInput.getText().toString().isEmpty())
        {
            binding.emailAddressInputError.setError("Email cannot be empty");
            isValidEmail = false;
        }
        // wrong format
        else if (!Patterns.EMAIL_ADDRESS.matcher(binding.emailAddressInput.getText().toString()).matches())
        {
            binding.emailAddressInputError.setError("Invalid Email Address");
            isValidEmail = false;
        }
        // input correct
        else
        {
            binding.emailAddressInputError.setErrorEnabled(false);
            isValidEmail = true;
        }

        if (isValidEmail)
        {
            // sign up
            binding.progressBar.setVisibility(View.VISIBLE);

            mAuth.createUserWithEmailAndPassword(binding.emailAddressInput.getText().toString(), binding.passwordInput.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                new Thread(() ->{
                                    try {
                                        Thread.sleep(10);
                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }
                                    runOnUiThread(() -> {
                                        Toast.makeText(SignUpActivity.this, "Sign Up successfully", Toast.LENGTH_SHORT).show();
                                        binding.progressBar.setVisibility(View.GONE);
                                    });
                                }).start();
                                FirebaseUser user = mAuth.getCurrentUser();
                                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            
        }

    }
}