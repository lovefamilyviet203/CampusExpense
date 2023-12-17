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
        // Set an onClickListener for the button with the id "buttonSave"
        findViewById(R.id.buttonSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get a reference to the EditText with the id "editTextExpenseName"
                EditText expenseNameControl = findViewById(R.id.editTextExpenseName);
                // Retrieve the text entered in the expense name EditText
                String expenseName = expenseNameControl.getText().toString();

                // Get a reference to the Spinner with the id "spinnerCategory"
                Spinner expenseTypeControl = findViewById(R.id.spinnerCategory);
                // Retrieve the selected item text from the expense type Spinner
                String expenseType = expenseTypeControl.getSelectedItem().toString();

                // Get a reference to the EditText with the id "editTextAmount"
                EditText expenseAmountControl = findViewById(R.id.editTextAmount);
                // Retrieve the text entered in the expense amount EditText
                String expenseAmount = expenseAmountControl.getText().toString();

                // Get a reference to the EditText with the id "editTextDate"
                EditText expenseDateControl = findViewById(R.id.editTextDate);
                // Retrieve the text entered in the expense date EditText
                String expenseDate = expenseDateControl.getText().toString();

                // Create an ExpenseEntity object and set its properties
                ExpenseEntity expense = new ExpenseEntity();
                expense.expenseName = expenseName;
                expense.amount = expenseAmount;
                expense.expenseType = expenseType;
                expense.expenseDate = expenseDate;

                // Create a DatabaseHelper instance
                DatabaseHelper dbHelper = new DatabaseHelper(getApplication());
                // Insert the expense into the database and get the generated ID
                long id = dbHelper.insertExpense(expense);

                // Display the generated ID as a Toast message
                Toast.makeText(getApplication(), String.valueOf(id), Toast.LENGTH_LONG).show();

                // Create an intent to start the ListExpenseActivity
                Intent intent = new Intent(getApplicationContext(), ListExpenseActivity.class);
                // Start the ListExpenseActivity
                startActivity(intent);
            }
        });

        // Set an onClickListener for the EditText with the id "editTextExpenseDate"
        editTextExpenseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an instance of the DatePickerFragment
                AddExpenseActivity.DatePickerFragment datePickerFragment = new AddExpenseActivity.DatePickerFragment();
                // Set the associated EditText for the selected date
                datePickerFragment.editText = editTextExpenseDate;
                // Show the DatePickerFragment using the FragmentManager
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