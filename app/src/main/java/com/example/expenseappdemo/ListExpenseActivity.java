package com.example.expenseappdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import database.DatabaseHelper;
import database.ExpenseEntity;


public class ListExpenseActivity extends AppCompatActivity {

    List<ExpenseEntity> listExpenseActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_expense);
        ImageButton backButton = findViewById(R.id.imageButtonBack);
        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
        listExpenseActivity =dbHelper.getAllExpenses();
        ArrayAdapter adapter = new ArrayAdapter<ExpenseEntity>(this, R.layout.activity_listview,dbHelper.getAllExpenses());
        ListView listView = (ListView) findViewById(R.id.listExpense);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ExpenseEntity selectedExpense = (ExpenseEntity) parent.getItemAtPosition(position);
                final String[] options = {"Detail","Delete","Update"};
                AlertDialog.Builder builder = new AlertDialog.Builder(ListExpenseActivity.this);
                builder.setItems(options,(dialog,item)->{
                    if(options[item] == "Delete"){
                        // Remove from the listview
                        listExpenseActivity.remove(position);
                        adapter.notifyDataSetChanged();
                        // Remove from the database
                        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
                        dbHelper.deleteExpense(selectedExpense.Id);
                        recreate();
                    }
                    else if(options[item] == "Update"){
                        Intent intent = new Intent(ListExpenseActivity.this, UpdateExpenseActivity.class);
                        intent.putExtra("expenseId", selectedExpense.Id); // Assuming ExpenseEntity has an Id field
                        startActivity(intent);                    }
                    else if(options[item] == "Detail"){
                        Intent intent = new Intent(ListExpenseActivity.this, DetailExpenseActivity.class);
                        intent.putExtra("expenseId", selectedExpense.Id); // Assuming ExpenseEntity has an Id field
                        startActivity(intent);                    }
                });
                builder.show();
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackAddExpense();
            }
        });
    }
    public void onBackAddExpense(){
        Intent intent = new Intent(this, AddExpenseActivity.class);
        startActivity(intent);
        finish();
    }
}