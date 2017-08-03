package com.example.arathi.balancesheet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.*;

/**
 * Created by arathi on 8/3/17.
 */

public class AccountDetailsAdapter extends BaseAdapter {

    private List<AccountDetails> accountDetailsList;
    private Context context;
    private int numItems;

    public AccountDetailsAdapter(List<AccountDetails> items, Context context){
    accountDetailsList = items;
        this.context = context;
        this.numItems = items.size();
    }

    @Override
    public int getCount() {
        return numItems;
    }

    @Override
    public Object getItem(int position) {
        return accountDetailsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final AccountDetails accountDetails = accountDetailsList.get(position);
        final RelativeLayout accountLayout =(RelativeLayout) LayoutInflater.from(context).inflate(
                R.layout.account_details, parent, false);
        TextView accountName = (TextView)accountLayout.findViewById(R.id.account_name);
        TextView accountNumber = (TextView)accountLayout.findViewById(R.id.account_number);
        TextView accountBalance = (TextView)accountLayout.findViewById(R.id.account_balance);

        accountName.setText(accountDetails.getAccountName());
        accountNumber.setText(accountDetails.getAccountNumber());
        String accBalance = Float.toString(accountDetails.getAccountBalance());
        accountBalance.setText(accBalance);

        return accountLayout;
    }
}

