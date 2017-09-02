package com.example.arathi.balancesheet.expenses;

import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.arathi.balancesheet.Accounts.AccountDetails;
import com.example.arathi.balancesheet.DBHelper;
import com.example.arathi.balancesheet.MainActivity;
import com.example.arathi.balancesheet.R;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;


/**
 * Created by arathi on 8/4/17.
 * This is used to add individual expense to the database.
 */

public class ExpenseEdit extends AppCompatActivity {



    final static String update = "Update";
    final static String save = "Save";
    static Button expenseDate;
    String DEBUG_TAG = "ExpenseEdit";
    Spinner expenseMode;
    Spinner expenseCategory;
    EditText expenseName;
    EditText expenseAmount;
    Button expenseSubmit;
    DBHelper dbHelper;
    int type_expense;
    CheckBox incomeOrNot;
    String[] expenseCat;
    String[] expenseMod;
    int expenseIndex;
    ArrayAdapter<String> adapterMode;
    ArrayAdapter<String> adapterCategory;

    public static void getChosenDate(int day, int month, int year) {
        String dateChosen = String.valueOf(day) + "/" +
                String.valueOf(month) + "/" + String.valueOf(year);
        expenseDate.setText(dateChosen);

    }

    private StringBuilder getCurrentDate(){
        final Calendar c = Calendar.getInstance();
        int date = c.get(Calendar.DATE);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        StringBuilder str = new StringBuilder();
        str.append(date);
        str.append("/");
        str.append(month+1);
        str.append("/");
        str.append(year);
        return str;
    }

    public String[] setExpenseMode(){
        dbHelper = DBHelper.getInstance(getApplicationContext());
        List<String> accountName = dbHelper.getAccountName();
        return accountName.toArray( new String[accountName.size()] );
    }

    boolean checkExpenseName(EditText expenseName){
        if(expenseName.getText().toString().trim().length()==0) {
            Toast.makeText(getApplicationContext(),"Add Expense Name",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    boolean checkExpenseAmount(EditText expenseAmount){
        String amt = expenseAmount.getText().toString();
        if(amt.length()==0) {
            return false;
        }
        if(Float.parseFloat(amt)<=0){
            Toast.makeText(getApplicationContext(),"Enter Positive Amount",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    boolean checkExpenseMode(Spinner expenseMode){
        return expenseMode.getAdapter().getCount() != 0;
    }
    void watcher(final EditText expenseName, final EditText expenseAmount, final Button expenseSubmit){
        expenseName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(checkExpenseName(expenseName) && checkExpenseAmount(expenseAmount) ){
                    if( checkExpenseMode(expenseMode)){
                        expenseSubmit.setEnabled(true);
                        expenseSubmit.setBackgroundColor(Color.GREEN); }
                    else {
                        Toast.makeText(getApplicationContext(),"Add account details first",Toast.LENGTH_SHORT).show();
                        expenseSubmit.setEnabled(false);
                        expenseSubmit.setBackgroundColor(Color.GRAY);
                    }
                } else {
                    expenseSubmit.setEnabled(false);
                    expenseSubmit.setBackgroundColor(Color.GRAY);

                }
            }
        });
        expenseAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(checkExpenseName(expenseName) && checkExpenseAmount(expenseAmount)) {
                    if (checkExpenseMode(expenseMode)) {
                        expenseSubmit.setEnabled(true);
                        expenseSubmit.setBackgroundColor(Color.GREEN);
                    } else {
                        expenseSubmit.setEnabled(false);
                        expenseSubmit.setBackgroundColor(Color.GRAY);
                        Toast.makeText(getApplicationContext(), "Add account details first", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    expenseSubmit.setEnabled(false);
                    expenseSubmit.setBackgroundColor(Color.GRAY);
                }
            }
        });
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

        expenseSubmit = (Button)findViewById(R.id.expenseSubmit);
        expenseSubmit.setEnabled(false);
        expenseSubmit.setBackgroundColor(Color.GRAY);

        expenseDate.setText(getCurrentDate());
        try {
            expenseMod = setExpenseMode();
            adapterMode = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, expenseMod);
            expenseMode.setAdapter(adapterMode);
        }catch (Exception e){
            Log.d(DEBUG_TAG,e.getMessage());
        }

        watcher(expenseName,expenseAmount,expenseSubmit);
        Intent intent = getIntent();
        type_expense = intent.getIntExtra(MainActivity.EXPENSE_TYPE,0);
        if(type_expense==MainActivity.EXPENSE_UPDATE){
            String expense_entry = intent.getStringExtra(MainActivity.EXPENSE_DATA);
            ExpenseDetail expenseDetail = (new Gson()).fromJson(expense_entry,ExpenseDetail.class);
            expenseName.setText(expenseDetail.getExpenseName());
            String expenseBalance = String.format(Locale.getDefault(),"%.2f",expenseDetail.getAmount());
            expenseAmount.setText(expenseBalance);
            String expenseModeCompare = expenseDetail.getExpenseMode();
            int spinnerPosition = adapterMode.getPosition(expenseModeCompare);
            expenseMode.setSelection(spinnerPosition);
            String expenseCategoryCompare = expenseDetail.getExpenseCategory();
            spinnerPosition = adapterCategory.getPosition(expenseCategoryCompare);
            expenseCategory.setSelection(spinnerPosition);
            expenseDate.setText(expenseDetail.getExpenseDate());
            int incornot = expenseDetail.getCheckIncomeOrExpense();
            if (incornot==1){
                incomeOrNot.setChecked(true);
            }
            expenseIndex = expenseDetail.getExpenseIndex();
            expenseSubmit.setText(update);
        }
        else {
            expenseSubmit.setText(save);
        }

    }
    @Override
    public void onResume(){

        Log.d(DEBUG_TAG,"Rechaed OnResume");
        super.onResume();
    }

    private void setAccount(AccountDetails account, float amount){
        dbHelper = DBHelper.getInstance(getApplicationContext());
        dbHelper.updateAccount(account.getaIndex(), account.getAccountName(), account.getAccountNumber(), amount);

    }
    public void setExpenseSubmit(View v){
        boolean submit = true;
        String mode = "";
        Log.d(DEBUG_TAG,"Reached Button Clicer for saving");
        String name = expenseName.getText().toString().trim();
        if(!checkExpenseName(expenseName)){
            submit = false;
            Snackbar.make(v, " Enter Expense name", Snackbar.LENGTH_SHORT).setAction("",null).show();
        }
        if(!checkExpenseMode(expenseMode)){
            submit=false;

        } else {
           mode = expenseMode.getSelectedItem().toString();
        }
        String category = expenseCategory.getSelectedItem().toString();
        String date = expenseDate.getText().toString();
        String amt = expenseAmount.getText().toString();
        if(!checkExpenseAmount(expenseAmount)){
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
                AccountDetails newAccount = dbHelper.getAccountEntry(mode);
                if(type_expense==MainActivity.EXPENSE_ADD){
                dbHelper.insertExpense(name, amount, category, mode, date, check);
                    if (check == 0) {
                        float balance = newAccount.getAccountBalance() - amount;
                        setAccount(newAccount,balance);
                    } else {
                        float balance = newAccount.getAccountBalance() + amount;
                        setAccount(newAccount,balance);
                    }
                } else{
                    ExpenseDetail expenseDetail = dbHelper.getExpenseDetail(expenseIndex);
                    AccountDetails previousAccount = dbHelper.getAccountEntry(expenseDetail.getExpenseMode());
                    float previousExpenseAmount = expenseDetail.getAmount();
                    int previouslyIncomeOrNot = expenseDetail.getCheckIncomeOrExpense();
                    float diff;
                    float newAmount;
                    float accountBalance = newAccount.getAccountBalance();
                    float prevAccountBalance = previousAccount.getAccountBalance();
                        if (previouslyIncomeOrNot == 0)
                            newAmount = prevAccountBalance + previousExpenseAmount;
                        else
                            newAmount = prevAccountBalance - previousExpenseAmount;
                        setAccount(previousAccount, newAmount);
                    newAccount = dbHelper.getAccountEntry(newAccount.getAccountName());
                    if (check == 0)
                        newAmount = newAccount.getAccountBalance() - amount;
                    else
                        newAmount= newAccount.getAccountBalance() + amount;
                    setAccount(newAccount,newAmount);
                    dbHelper.updateExpense(expenseIndex,name, amount, category, mode, date, check);
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
