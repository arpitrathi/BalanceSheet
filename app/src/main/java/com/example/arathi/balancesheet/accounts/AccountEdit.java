package com.example.arathi.balancesheet.accounts;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.arathi.balancesheet.DBHelper;
import com.example.arathi.balancesheet.R;

/**
 * Created by arathi on 8/13/17.
 * This file is used to add account in the database.
 */

public class AccountEdit extends AppCompatActivity {
    EditText accountName;
    EditText accountNumber;
    EditText accountBalance;
    Button saveAccount;
    DBHelper dbHelper;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_entry);

        accountName = (EditText)findViewById(R.id.accountName);
        accountNumber = (EditText)findViewById(R.id.accountNumber);
        accountBalance = (EditText)findViewById(R.id.accountAmount);
        saveAccount = (Button)findViewById(R.id.saveAccount);

    }

    public void addAccount(View v){
        boolean submit = true;

        String name = accountName.getText().toString().trim();
        if(name.length()==0){
            submit = false;
            Snackbar.make(v, "Enter account name", Snackbar.LENGTH_LONG).setAction("",null).show();
        }
        String number = accountNumber.getText().toString().trim();
        if(number.length()==0){
            number = "NA";
        }
        String bal = accountBalance.getText().toString().trim();
        if(bal.length()==0){
            submit = false;
            Snackbar.make(v, "Enter account balance", Snackbar.LENGTH_LONG).setAction("",null).show();
        }

        dbHelper = DBHelper.getInstance(getApplicationContext());
        AccountDetails account = dbHelper.getAccountEntry(name);
        if( account != null){
            submit = false;
            Snackbar.make(v, "Account Name already exists", Snackbar.LENGTH_LONG).setAction("",null).show();
        }
        else {
            if(submit) {
                float balance = Float.parseFloat(bal);
                try {
                    dbHelper.insertAccount(name, number, balance);
                    Snackbar.make(v, "Account Details Added", Snackbar.LENGTH_SHORT).setAction("", null).show();
                    finish();
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                }
            }
        }
    }
}
