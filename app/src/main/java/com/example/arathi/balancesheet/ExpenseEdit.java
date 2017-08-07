package com.example.arathi.balancesheet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

//import com.example.arathi.balancesheet.MainActivity;
/**
 * Created by arathi on 8/4/17.
 */

public class ExpenseEdit extends AppCompatActivity {

    private String[] expenseCat;
    private String[] expenseMod;
    Spinner expenseMode;
    Spinner expenseCategory;
    EditText expenseName;
    EditText expenseAmount;
    Button expenseDate;
    Button expenseSubmit;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expense_entry);

        expenseCat = new String[]{"Account Transfer","Personal","Investment",  "Loan Given", "Loan Taken" };
        expenseMod = new String[]{"Cash","Paytm","Andhra Bank","Citibank","Citibank CC","HDFC CC","SBI"};

        expenseMode = (Spinner)findViewById(R.id.expenseMode);
        ArrayAdapter<String> adapterMode = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item, expenseMod);
        expenseMode.setAdapter(adapterMode);
        expenseCategory = (Spinner)findViewById(R.id.expenseCategory);
        ArrayAdapter<String> adapterCategory = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item, expenseCat);
        expenseCategory.setAdapter(adapterCategory);
        expenseName = (EditText)findViewById(R.id.expenseName);
        expenseAmount = (EditText)findViewById(R.id.expenseAmount);
        expenseDate = (Button)findViewById(R.id.expenseDate);
        expenseDate.setText(MainActivity.getCurrentDate());
        expenseSubmit = (Button)findViewById(R.id.expenseSubmit);


    }

    public void getExpenseData(View v){

    }

}
