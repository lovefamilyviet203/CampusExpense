package com.example.expenseappdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import database.DatabaseHelper;
import database.ExpenseEntity;


public class ListExpenseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_expense);
        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
        ArrayAdapter adapter = new ArrayAdapter<ExpenseEntity>(this, R.layout.activity_listview,dbHelper.getAllExpenses());
        ListView listView = (ListView) findViewById(R.id.listExpense);
        listView.setAdapter(adapter);
    }
}