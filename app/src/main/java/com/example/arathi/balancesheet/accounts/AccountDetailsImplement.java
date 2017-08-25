package com.example.arathi.balancesheet.accounts;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.example.arathi.balancesheet.DBHelper;
import com.example.arathi.balancesheet.R;

import java.util.List;

public class AccountDetailsImplement extends AppCompatActivity {

    List<AccountDetails> accountDetails;
    ListView lv;
    AccountDetailsAdapter accountDetailsAdapter;
    private DBHelper dbHelper;

    private void setAccountDetails(){
        Log.d("Accounts","Set Account Details");
        try{
            dbHelper = DBHelper.getInstance(getApplicationContext());
            accountDetails = dbHelper.getAccountDetailsList();

            lv = (ListView)findViewById(R.id.account_detail);
            accountDetailsAdapter = new AccountDetailsAdapter(accountDetails, this);    
            lv.setAdapter(accountDetailsAdapter);
            dbHelper.close();
        }catch (Exception e){
            Log.e("Error",e.getMessage());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts_display);
        Log.d("Account", " OnCreate Implement");
       
        FloatingActionButton fabaccount = (FloatingActionButton)findViewById(R.id.accountAdd);
        fabaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountDetailsImplement.this, AccountEdit.class);
                startActivity(intent);
            }
        }
        );
        setAccountDetails();

    }

    @Override
    public void onResume(){

        super.onResume();
        Log.d("Account", " OnResume");
        setAccountDetails();

    }

}
