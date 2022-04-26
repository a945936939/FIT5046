package com.example.assignment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.assignment.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        mAuth = FirebaseAuth.getInstance();

        binding.signupTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                // intent.putExtra();
                startActivity(intent);
            }
        });

        binding.loginButton.setOnClickListener(v ->{
            if ((binding.usernameInput.getText().toString().equals(""))||(binding.passwordInput.getText().toString().equals("")))
            {
                Toast.makeText(LoginActivity.this, "Login failed.",
                        Toast.LENGTH_SHORT).show();
            }
            else
            {
                mAuth.signInWithEmailAndPassword(binding.usernameInput.getText().toString(), binding.passwordInput.getText().toString())
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    // Toast.makeText(LoginActivity.this, "Login successfully.",Toast.LENGTH_SHORT).show();

                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(LoginActivity.this, "Login failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        authStateListener = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null)
            {
                Toast.makeText(LoginActivity.this, "Welcome! You have already Logged in.",
                        Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        };
    }

    @Override
    public void onStart()
    {
        super.onStart();
        mAuth.addAuthStateListener(authStateListener);
    }

    @Override
    public void onStop()
    {
        super.onStop();
        if (authStateListener != null) {
            mAuth.removeAuthStateListener(authStateListener);
        }
    }
}