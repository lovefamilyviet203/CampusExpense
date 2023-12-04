package com.example.expenseappdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import database.DatabaseHelper;
import database.UserEntity;

public class SignupActivity extends AppCompatActivity {

    EditText usernameEditText, emailEditText, passwordEditText;
    Button signUpButton;
    DatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        dbHelper = new DatabaseHelper(this);

        usernameEditText = findViewById(R.id.editTextUsername);
        emailEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextPassword);
        signUpButton = findViewById(R.id.button4);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString();

                // Validate user input
                if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(SignupActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check if the email is already registered
                if (dbHelper.checkUserEmailExists(email)) {
                    Toast.makeText(SignupActivity.this, "Email already registered", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Insert user into the database
                UserEntity newUser = new UserEntity();
                newUser.username = username;
                newUser.email = email;
                newUser.password = password;

                long result = dbHelper.insertUser(newUser);

                if (result != -1) {
                    Toast.makeText(SignupActivity.this, "Registration successful!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(SignupActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}