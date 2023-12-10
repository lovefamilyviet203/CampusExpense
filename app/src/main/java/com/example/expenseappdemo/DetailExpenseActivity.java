package com.example.expenseappdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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

            // Populate the UI with expense details
            expenseNameTextView.setText("Expense Name: " + expense.getExpenseName());
            expenseAmountTextView.setText("Amount: " + expense.getAmount());
            expenseDateTextView.setText("Date : " + expense.getExpenseDate());
            expenseCategoryTextView.setText("Category : " + expense.getExpenseType());
            // Update other UI elements

            backButtonForm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackListExpense();
                }
            });

            detailToUpdateButtonForm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDetailtoUpdate(expense);
                }
            });

            detailToHomeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDetailtoHomeButtonClick();
                }
            });
        } else {
            // Handle the case where expenseId is -1 (invalid)
            Toast.makeText(this, "Invalid Expense ID", Toast.LENGTH_SHORT).show();
            finish(); // Finish the activity if the expense ID is invalid
        }
    }

    public void onBackListExpense() {
        Intent intent = new Intent(this, ListExpenseActivity.class);
        startActivity(intent);
        finish();
    }

    public void onDetailtoUpdate(ExpenseEntity selectedExpense) {
        Intent intent = new Intent(this, UpdateExpenseActivity.class);
        intent.putExtra("expenseId", selectedExpense.getId());
        intent.putExtra("expenseAmount", selectedExpense.getAmount());
        intent.putExtra("expenseDate", selectedExpense.getExpenseDate()); // Assuming date is a long
        startActivity(intent);
        finish();
    }

    public void onDetailtoHomeButtonClick() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
