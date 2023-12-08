package com.example.expenseappdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Calendar;

import database.DatabaseHelper;
import database.ExpenseEntity;

public class UpdateExpenseActivity extends AppCompatActivity {

    private EditText editTextExpenseName;
    private EditText editTextExpenseAmount;
    private EditText editTextExpenseDate;
    private Spinner spinnerCategory;
    private Button buttonUpdateExpense;

    private int expenseId; // Expense ID passed from ListExpenseActivity
    private DatabaseHelper dbHelper;
    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        public EditText editText;
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker.
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it.
            return new DatePickerDialog(requireContext(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date the user picks.
            editText.setText(day +"/" + month + "/" + year);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_expense);
        // Initialize UI elements
        editTextExpenseName = findViewById(R.id.editTextExpenseName);
        editTextExpenseAmount = findViewById(R.id.editTextAmount);
        editTextExpenseDate = findViewById(R.id.editTextDate);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        buttonUpdateExpense = findViewById(R.id.buttonUpdate);
        dbHelper = new DatabaseHelper(getApplicationContext());

        Intent intent = getIntent();
        if (intent != null) {
            expenseId = intent.getIntExtra("expenseId", -1);
        }

        ExpenseEntity expense = dbHelper.getExpenseById(expenseId);

        if (expense != null) {
            editTextExpenseName.setText(expense.getExpenseName());
            editTextExpenseAmount.setText(expense.getAmount());
            editTextExpenseDate.setText(expense.getExpenseDate());

            // Populate other UI elements as needed
            setSpinnerSelection(expense.getExpenseType());
        }
        editTextExpenseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddExpenseActivity.DatePickerFragment datePickerFragment = new AddExpenseActivity.DatePickerFragment();
                datePickerFragment.editText = editTextExpenseDate;
                datePickerFragment.show(getSupportFragmentManager(),"dataPicker");
            }
        });

        buttonUpdateExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateExpense();
            }
        });
    }
    private void setSpinnerSelection(String expenseType) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.expenseType, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);

        int position = adapter.getPosition(expenseType);
        spinnerCategory.setSelection(position);
    }

    private void updateExpense() {
        String updatedName = editTextExpenseName.getText().toString().trim();
        String updatedAmount = editTextExpenseAmount.getText().toString().trim();
        String updatedDate = editTextExpenseDate.getText().toString().trim();
        String updatedType = spinnerCategory.getSelectedItem().toString();
        // You may get other updated values from UI as needed

        ExpenseEntity updatedExpense = new ExpenseEntity();
        updatedExpense.setId(expenseId);
        updatedExpense.setExpenseName(updatedName);
        updatedExpense.setAmount(updatedAmount);
        updatedExpense.setExpenseDate(updatedDate);
        updatedExpense.setExpenseType(updatedType);
        // Set other attributes as needed

        dbHelper.updateExpense(updatedExpense);

        Intent intent = new Intent(UpdateExpenseActivity.this, ListExpenseActivity.class);
        startActivity(intent);
        finish();
    }
}