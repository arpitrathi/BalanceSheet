package com.example.arathi.balancesheet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class AccountDetailsImplement extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        List<AccountDetails> accountDetails = new ArrayList<>();
        accountDetails.add(new AccountDetails(1,"Paytm","9453467714",780));
        accountDetails.add(new AccountDetails(2,"Cash","0000",115));
        accountDetails.add(new AccountDetails(3,"Cashdajsdbjdsv","0000",115));
        accountDetails.add(new AccountDetails(4,"Cash","0000",115));
        accountDetails.add(new AccountDetails(4,"Cash","0000",115));
        accountDetails.add(new AccountDetails(5,"Casaascascsh","0000",115));
        accountDetails.add(new AccountDetails(6,"Cash","0000",115));
        accountDetails.add(new AccountDetails(7,"Cascacsch","0000",115));
        accountDetails.add(new AccountDetails(8,"Cash","0000",115));
        accountDetails.add(new AccountDetails(9,"Cash","0000",115));
        ListView lv = (ListView)findViewById(R.id.account_detail);
        AccountDetailsAdapter accountDetailsAdapter = new AccountDetailsAdapter(accountDetails, this);
        lv.setAdapter(accountDetailsAdapter);

    }
}
