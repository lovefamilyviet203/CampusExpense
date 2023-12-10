package com.example.expenseappdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import database.DatabaseHelper;
import database.ExpenseEntity;


public class ListExpenseActivity extends AppCompatActivity {

    List<ExpenseEntity> listExpenseActivity;
    private ArrayAdapter<ExpenseEntity> adapter;
    private DatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_expense);
        ImageButton backButton = findViewById(R.id.imageButtonBack);
        EditText searchEditText = findViewById(R.id.editTextText4);
        ListView listView = findViewById(R.id.listExpense);

        dbHelper = new DatabaseHelper(getApplicationContext());
        listExpenseActivity = dbHelper.getAllExpenses();
        adapter = new ArrayAdapter<>(this, R.layout.activity_listview, listExpenseActivity);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            ExpenseEntity selectedExpense = (ExpenseEntity) parent.getItemAtPosition(position);
            showOptionsDialog(selectedExpense);
        });

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                filterList(s.toString());
            }
        });

        backButton.setOnClickListener(v -> onBackAddExpense());
    }

    private void filterList(String query) {
        List<ExpenseEntity> filteredList = dbHelper.getAllExpenses(query);
        adapter.clear();
        adapter.addAll(filteredList);
        adapter.notifyDataSetChanged();

        // Show a Snackbar if no matching results are found
        if (filteredList.isEmpty()) {
            showNoResultsSnackbar();
        }
    }

    private void showOptionsDialog(ExpenseEntity selectedExpense) {
        final String[] options = {"Detail", "Delete", "Update"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(options, (dialog, item) -> {
            if (options[item].equals("Delete")) {
                deleteExpense(selectedExpense);
            } else if (options[item].equals("Update")) {
                updateExpense(selectedExpense);
            } else if (options[item].equals("Detail")) {
                showExpenseDetail(selectedExpense);
            }
        });
        builder.show();
    }
    private void deleteExpense(ExpenseEntity selectedExpense) {
        listExpenseActivity.remove(selectedExpense);
        adapter.notifyDataSetChanged();
        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
        dbHelper.deleteExpense(selectedExpense.getId());
        recreate();
    }

    private void updateExpense(ExpenseEntity selectedExpense) {
        Intent intent = new Intent(ListExpenseActivity.this, UpdateExpenseActivity.class);
        intent.putExtra("expenseId", selectedExpense.getId());
        startActivity(intent);
    }

    private void showExpenseDetail(ExpenseEntity selectedExpense) {
        Intent intent = new Intent(ListExpenseActivity.this, DetailExpenseActivity.class);
        intent.putExtra("expenseId", selectedExpense.getId());
        startActivity(intent);
    }

    private void onBackAddExpense() {
        Intent intent = new Intent(this, AddExpenseActivity.class);
        startActivity(intent);
        finish();
    }

    private void showNoResultsSnackbar() {
        View parentLayout = findViewById(android.R.id.content);
        Snackbar.make(parentLayout, "No matching results found", Snackbar.LENGTH_SHORT).show();
    }
}
