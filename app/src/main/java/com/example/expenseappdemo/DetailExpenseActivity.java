package com.example.expenseappdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import database.DatabaseHelper;
import database.ExpenseEntity;

public class DetailExpenseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_expense);
        ImageButton backButtonForm = findViewById(R.id.imageButtonBackForm);
        ImageButton detailToUpdateButtonForm = findViewById(R.id.imageButtonDetailToUpdate);
        ImageButton detailToHomeButton = findViewById(R.id.imageButtonDetailToHome);
        int expenseId = getIntent().getIntExtra("expenseId", -1);

        if (expenseId != -1) {
            // Retrieve the detailed information for the selected expense
            DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
            ExpenseEntity expense = dbHelper.getExpenseById(expenseId);

            // Use the data to populate the UI or perform other actions
            TextView expenseNameTextView = findViewById(R.id.expenseNameTextView1);
            TextView expenseAmountTextView = findViewById(R.id.expenseAmountTextView2);
            TextView expenseDateTextView = findViewById(R.id.expenseDateTextView2);
            TextView expenseCategoryTextView = findViewById(R.id.expenseCategoryTextView2);

            // Add more TextViews or other UI elements as needed

            // Populate the UI with expense details
            expenseNameTextView.setText("Expense Name: " + expense.expenseName);
            expenseAmountTextView.setText("Amount: " + expense.amount);
            expenseDateTextView.setText("Date : " + expense.expenseDate);
            expenseCategoryTextView.setText("Category : " + expense.expenseType);
            // Update other UI elements
        }
        backButtonForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                onBackListExpense();
            }
        });
        detailToUpdateButtonForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDetailtoUpdate();
            }
        });
        detailToHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDetailtoHomeButtonClick();
            }
        });

    }
    public void onBackListExpense(){
        Intent intent = new Intent(this, ListExpenseActivity.class);
        startActivity(intent);
        finish();
    }
    public void onDetailtoUpdate() {
        int expenseId = getIntent().getIntExtra("expenseId", -1);

        if (expenseId != -1) {
            // Retrieve the detailed information for the selected expense
            DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
            ExpenseEntity expense = dbHelper.getExpenseById(expenseId);

            // Pass the expense information to UpdateExpenseActivity
            Intent intent = new Intent(this, UpdateExpenseActivity.class);
            //get ID  va
            intent.putExtra("expenseId", expense.getId());
            intent.putExtra("expenseName", expense.expenseName);
            intent.putExtra("amount", expense.amount);
            intent.putExtra("expenseDate", expense.expenseDate);
            intent.putExtra("expenseType", expense.expenseType);
            startActivity(intent);
            finish();
        }
    }
    public void onDetailtoHomeButtonClick(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}