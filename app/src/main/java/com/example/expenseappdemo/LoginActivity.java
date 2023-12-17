package com.example.expenseappdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import database.DatabaseHelper;

public class LoginActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    Button loginButton;
    Button signUpButton;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dbHelper = new DatabaseHelper(this);

        email = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editTextPassword);
        loginButton = findViewById(R.id.button3);
        signUpButton = findViewById(R.id.Signupbutton);  // Assuming you have a SignUp button

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the user's email and password from the corresponding EditText fields, trimming any leading or trailing whitespaces
                String userEmail = email.getText().toString().trim();
                String userPassword = password.getText().toString().trim();
                // Save the user's email and password to SharedPreferences (assuming this method is implemented elsewhere)
                saveUserToSharedPreferences(userEmail, userPassword);
                // Check user credentials using the DatabaseHelper instance (dbHelper)
                if (dbHelper.checkUserCredentials(userEmail, userPassword)) {
                    // If credentials are valid, display a success message and navigate to the MainActivity
                    Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    // Finish the LoginActivity to prevent going back to it from the MainActivity
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to SignUpActivity
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }
    private void saveUserToSharedPreferences(String userEmail, String userPassword) {
        // Get a reference to the SharedPreferences instance named "user_data" with private access
        SharedPreferences preferences = getSharedPreferences("user_data", MODE_PRIVATE);
        // Get an editor to modify the SharedPreferences data
        SharedPreferences.Editor editor = preferences.edit();
        // Put the user's email and password into SharedPreferences
        editor.putString("userEmail", userEmail);
        editor.putString("userPassword", userPassword);
        editor.apply();
    }
}