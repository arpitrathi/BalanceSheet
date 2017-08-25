package com.example.arathi.balancesheet.expense;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.arathi.balancesheet.DBHelper;
import com.example.arathi.balancesheet.MainActivity;
import com.example.arathi.balancesheet.R;
import com.example.arathi.balancesheet.accounts.AccountDetails;

import java.util.List;


/**
 * Created by arathi on 8/4/17.
 * This is used to add individual expense to the database.
 */

public class ExpenseEdit extends AppCompatActivity {



    static Button expenseDate;
    String DEBUG_TAG = "ExpenseEdit";
    Spinner expenseMode;
    Spinner expenseCategory;
    EditText expenseName;
    EditText expenseAmount;
    Button expenseSubmit;
    DBHelper dbHelper;

    CheckBox incomeOrNot;
    String[] expenseCat;
    String[] expenseMod;
    ArrayAdapter<String> adapterMode;
    ArrayAdapter<String> adapterCategory;



    public static void getChosenDate(int day, int month, int year) {
        String dateChosen = String.valueOf(day) + "/" +
                String.valueOf(month) + "/" + String.valueOf(year);
        expenseDate.setText(dateChosen);

    }

    public String[] setExpenseMode(){
        dbHelper = DBHelper.getInstance(getApplicationContext());
        List<String> accountName = dbHelper.getAccountName();
        return accountName.toArray( new String[accountName.size()] );
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        Log.d(DEBUG_TAG,"Reached Oncreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expense_entry);

        expenseCat = new String[]{"Account Transfer","Personal","Investment",  "Loan Given", "Loan Taken" };

        adapterCategory = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item, expenseCat);

        expenseMode = (Spinner)findViewById(R.id.expenseMode);
        expenseCategory = (Spinner)findViewById(R.id.expenseCategory);
        expenseCategory.setAdapter(adapterCategory);
        expenseName = (EditText)findViewById(R.id.expenseName);
        expenseAmount = (EditText)findViewById(R.id.expenseAmount);
        incomeOrNot = (CheckBox)findViewById(R.id.expenseOrIncome);
        expenseDate = (Button)findViewById(R.id.expenseDate);
        expenseDate.setText(MainActivity.getCurrentDate());
        expenseSubmit = (Button)findViewById(R.id.expenseSubmit);


    }
    @Override
    public void onResume(){

        Log.d(DEBUG_TAG,"Rechaed OnResume");
        super.onResume();

        try {
            expenseMod = setExpenseMode();
        }catch (Exception e){
            Log.d(DEBUG_TAG,e.getMessage());
        }

        try {
            adapterMode = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, expenseMod);
            expenseMode.setAdapter(adapterMode);
        }catch (Exception e) {
            Log.e("Error",e.getMessage());
        }
    }

    public void setExpenseSubmit(View v){
        boolean submit = true;

        Log.d(DEBUG_TAG,"Reached Button Clicer for saving");
        String name = expenseName.getText().toString().trim();
        if(name.length()==0 ){
            submit = false;
            Snackbar.make(v, " Enter Expense name", Snackbar.LENGTH_SHORT).setAction("",null).show();
        }

        String mode = expenseMode.getSelectedItem().toString();
        String category = expenseCategory.getSelectedItem().toString();
        String date = expenseDate.getText().toString();
        String amt = expenseAmount.getText().toString();
        if(amt.length()==0){
            submit = false;
            Snackbar.make(v, " Add amount", Snackbar.LENGTH_SHORT).setAction("",null).show();

        }
        float amount=0;

        boolean incOrNot =  incomeOrNot.isChecked();
        int check= incOrNot?1:0;



        if(submit) {
            try {
                amount = Float.parseFloat(amt);
                if(amount<=0){
                    submit = false;
                    Snackbar.make(v, " Enter positive balance", Snackbar.LENGTH_SHORT).setAction("",null).show();
                }
            }catch (Exception e){
                Log.d("Debug",e.getMessage());
            }

            try {
                dbHelper = DBHelper.getInstance(getApplicationContext());

                dbHelper.insertExpense(name, amount, category, mode, date, check);

                AccountDetails account = dbHelper.getAccountEntry(mode);
                if (check == 0) {
                    float balance = account.getAccountBalance() - amount;
                    dbHelper.updateAccount(account.getaIndex(), account.getAccountName(), account.getAccountNumber(), balance);
                } else {
                    float balance = account.getAccountBalance() + amount;
                    dbHelper.updateAccount(account.getaIndex(), account.getAccountName(), account.getAccountNumber(), balance);

                }

                Snackbar.make(v, "Expense Added", Snackbar.LENGTH_SHORT).setAction("", null).show();
                finish();
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
            }
        }
    }

    public void getDate(View v){
        Log.d(DEBUG_TAG,"Reached Date Fetcher");
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(),"DatePicker");


    }




}
