package com.example.arathi.balancesheet.Accounts;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.arathi.balancesheet.DBHelper;
import com.example.arathi.balancesheet.R;
import com.google.gson.Gson;

import java.util.Locale;

import static com.example.arathi.balancesheet.Accounts.AccountDetailsImplement.ACCOUNT_ADD;
import static com.example.arathi.balancesheet.Accounts.AccountDetailsImplement.ACCOUNT_DATA;
import static com.example.arathi.balancesheet.Accounts.AccountDetailsImplement.ACCOUNT_TYPE;

/**
 * Created by arathi on 8/13/17.
 * This file is used to add account in the database.
 */

public class AccountEdit extends AppCompatActivity {
    final static String update = "Update";
    final static String save = "Save";
    EditText accountName;
    EditText accountNumber;
    EditText accountBalance;
    Button saveAccount;
    DBHelper dbHelper;
    int accountId;
    int accountModifyType;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_entry);

        accountName = (EditText)findViewById(R.id.accountName);
        accountNumber = (EditText)findViewById(R.id.accountNumber);
        accountBalance = (EditText)findViewById(R.id.accountAmount);
        saveAccount = (Button)findViewById(R.id.saveAccount);
        watcher(accountName, accountBalance, saveAccount);
        Intent intent = getIntent();
        accountModifyType = intent.getIntExtra(ACCOUNT_TYPE, 0);
        if (accountModifyType == ACCOUNT_ADD) {
            saveAccount.setText(save);
        } else {
            String account = intent.getStringExtra(ACCOUNT_DATA);
            AccountDetails accountDetail = (new Gson()).fromJson(account, AccountDetails.class);
            accountName.setText(accountDetail.getAccountName());
            accountNumber.setText(accountDetail.getAccountNumber());
            String balance = String.format(Locale.getDefault(), "%.2f", accountDetail.getAccountBalance());
            accountBalance.setText(balance);
            accountId = accountDetail.getaIndex();
            saveAccount.setText(update);
        }
        saveAccount.setEnabled(false);
        watcher(accountName,accountBalance,saveAccount);

    }
    boolean checkAccountName(EditText accountName){
        dbHelper = DBHelper.getInstance(getApplicationContext());
        //AccountDetails account = dbHelper.getAccountEntry(accountName.getText().toString());
        if(accountName.getText().toString().trim().length()==0){
            return false;
        }
        /*
        if(account!=null) {
            Toast.makeText(getApplicationContext(),"Account Name already exists",Toast.LENGTH_LONG).show();
            //Snackbar.make(v, "Account Name already exists", Snackbar.LENGTH_LONG).setAction("",null).show();
            return false;
        }*/
        return true;
    }

    boolean checkAccountBalance(EditText accountBalance){
        String bal = accountBalance.getText().toString().trim();
        boolean returnVal = (bal.length() != 0);
        try {

            float balance = Float.parseFloat(bal);

            if (balance == 0) {
                return false;
            }
        } catch (Exception e) {
            Log.e("Error", "No Balance");
        }
        return returnVal;

    }
    void watcher(final EditText accountName, final EditText accountBalance, final Button saveAccount){
        accountName.addTextChangedListener(new TextWatcher() {
                                               @Override
                                               public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                               }

                                               @Override
                                               public void onTextChanged(CharSequence s, int start, int before, int count) {

                                               }

                                               @Override
                                               public void afterTextChanged(Editable s) {
                                                   if (checkAccountName(accountName) && checkAccountBalance(accountBalance)) {
                                                       saveAccount.setEnabled(true);
                                                       saveAccount.setBackgroundColor(Color.GREEN);
                                                   } else {
                                                       saveAccount.setEnabled(false);
                                                       saveAccount.setBackgroundColor(Color.GRAY);
                                                   }

                                               }
                                           }

        );
        accountBalance.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(checkAccountName(accountName) && checkAccountBalance(accountBalance)){
                    saveAccount.setEnabled(true);
                    saveAccount.setBackgroundColor(Color.GREEN);
                }
                else{
                    saveAccount.setEnabled(false);
                    saveAccount.setBackgroundColor(Color.GRAY);
                }

            }
        });
    }
    public void addAccount(View v){
        boolean submit = true;

        String name = accountName.getText().toString().trim();
        if(!checkAccountName(accountName)){
            submit = false;
            Snackbar.make(v, "Enter account name", Snackbar.LENGTH_LONG).setAction("",null).show();
        }
        String number = accountNumber.getText().toString().trim();
        if(number.length()==0){
            number = "NA";
        }
        String bal = accountBalance.getText().toString().trim();
        if(!checkAccountBalance(accountBalance)){
            submit = false;
            Snackbar.make(v, "Enter account balance", Snackbar.LENGTH_LONG).setAction("",null).show();
        }

        if(submit) {
            float balance = Float.parseFloat(bal);
            if (accountModifyType == ACCOUNT_ADD) {
                try {
                    dbHelper.insertAccount(name, number, balance);
                    Snackbar.make(v, "Account Details Added", Snackbar.LENGTH_SHORT).setAction("", null).show();
                    finish();
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                }
            } else {
                try {
                    dbHelper.updateAccount(accountId, name, number, balance);
                    finish();
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                }
            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
