package com.example.arathi.balancesheet.Accounts;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.arathi.balancesheet.DBHelper;
import com.example.arathi.balancesheet.R;
import com.google.gson.Gson;

import java.util.List;

public class AccountDetailsImplement extends AppCompatActivity {

    public static final String ACCOUNT_TYPE = "account_type";
    public static final int ACCOUNT_UPDATE = 2;
    public static final int ACCOUNT_ADD = 1;
    public static final String ACCOUNT_DATA = "account_data";
    List<AccountDetails> accountDetails;
    ListView lv;
    AccountDetailsAdapter accountDetailsAdapter;
    Intent intent;
    TextView emptyAccount;
    private DBHelper dbHelper;

    private void setAccountDetails(){
        Log.d("Accounts","Set Account Details");
        try{
            dbHelper = DBHelper.getInstance(getApplicationContext());
            accountDetails = dbHelper.getAccountDetailsList();

            lv = (ListView)findViewById(R.id.account_detail);
            accountDetailsAdapter = new AccountDetailsAdapter(accountDetails, this);    
            lv.setAdapter(accountDetailsAdapter);
            if(accountDetails.size()==0){

                emptyAccount.setVisibility(View.VISIBLE);
            }
            else {
                emptyAccount.setVisibility(View.GONE);
            }
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

        FloatingActionButton fabaccount = (FloatingActionButton) findViewById(R.id.accountAdd);
        fabaccount.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              intent = new Intent(AccountDetailsImplement.this, AccountEdit.class);
                                              intent.putExtra(ACCOUNT_TYPE, ACCOUNT_ADD);
                                              startActivity(intent);

                                          }
                                      }
        );
        setAccountDetails();

        emptyAccount = (TextView) findViewById(R.id.empty_text_view_account);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AccountDetails accountDetails = (AccountDetails) parent.getItemAtPosition(position);
                intent = new Intent(AccountDetailsImplement.this, AccountEdit.class);
                intent.putExtra(ACCOUNT_TYPE, ACCOUNT_UPDATE);
                String account = (new Gson()).toJson(accountDetails);
                intent.putExtra(ACCOUNT_DATA, account);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onResume(){

        super.onResume();
        Log.d("Account", " OnResume");
        setAccountDetails();

    }

}
