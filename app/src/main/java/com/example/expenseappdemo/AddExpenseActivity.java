package com.example.expenseappdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

import database.DatabaseHelper;
import database.ExpenseEntity;

public class AddExpenseActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_add_expense);
        EditText editTextExpenseDate = findViewById(R.id.editTextDate);
        findViewById(R.id.buttonSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText expenseNameControl = findViewById(R.id.editTextExpenseName);
                String expenseName = expenseNameControl.getText().toString();
                Spinner expenseTypeControl = findViewById(R.id.spinnerCategory);
                String expenseType = expenseTypeControl.getSelectedItem().toString();
                EditText expenseAmountControl = findViewById(R.id.editTextAmount);
                String expenseAmount = expenseAmountControl.getText().toString();
                EditText expenseDateControl = findViewById(R.id.editTextDate);
                String expenseDate = expenseDateControl.getText().toString();

                ExpenseEntity expense = new ExpenseEntity();
                expense.expenseName = expenseName;
                expense.amount = expenseAmount;
                expense.expenseType = expenseType;
                expense.expenseDate = expenseDate;
                DatabaseHelper dbHelper = new DatabaseHelper(getApplication());
                long id = dbHelper.insertExpense(expense);
                Toast.makeText(getApplication(), String.valueOf(id), Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getApplicationContext(), ListExpenseActivity.class);
                startActivity(intent);
            }
        });
        editTextExpenseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddExpenseActivity.DatePickerFragment datePickerFragment = new AddExpenseActivity.DatePickerFragment();
                datePickerFragment.editText = editTextExpenseDate;
                datePickerFragment.show(getSupportFragmentManager(),"dataPicker");
            }
        });

    }
    public void onHomeButtonClick(View view) {
        // Quay lại MainActivity khi ImageButton được nhấn
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    public void onViewButtonClick(View view){
        Intent intent = new Intent(this, ListExpenseActivity.class);
        startActivity(intent);
        finish();
    }
}