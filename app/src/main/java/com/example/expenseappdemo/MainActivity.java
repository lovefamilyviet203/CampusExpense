package com.example.expenseappdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import database.DatabaseHelper;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Kiểm tra xem có thông tin người dùng đã được lưu không
        SharedPreferences preferences = getSharedPreferences("user_data", MODE_PRIVATE);
        String userEmail = preferences.getString("userEmail", "");
        String userPassword = preferences.getString("userPassword", "");

        // Hiển thị tên người dùng
        displayUsername(userEmail, userPassword);
        ImageButton imageButtonHome = findViewById(R.id.imageButtonHome);
        imageButtonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Load lại trang khi ImageButton được nhấn
                recreate();
            }
        });
    }
    private void displayUsername(String userEmail, String userPassword) {
        DatabaseHelper dbHelper = new DatabaseHelper(this);

        if (dbHelper.checkUserCredentials(userEmail, userPassword)) {
            String username = dbHelper.getUsername(userEmail);
            if (username != null) {
                TextView usernameTextView = findViewById(R.id.usernameTextView);
                usernameTextView.setText("Welcome, " + username + "!");
            }
        }
    }
    public void onAddExpenseButtonClick(View view) {
        // Xử lý khi ImageButton được nhấn
        Intent intent = new Intent(this, AddExpenseActivity.class);
        startActivity(intent);
    }
    public void onListExpenseActivity(View view) {
        // Xử lý khi ImageButton được nhấn
        Intent intent = new Intent(this, ListExpenseActivity.class);
        startActivity(intent);
    }
    public void homeOnSetting(View view){
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }
    public void onHomeButtonClick(View view) {
        // Handle the home button click here if needed
        recreate();
    }

}