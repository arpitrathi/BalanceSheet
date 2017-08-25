package com.example.arathi.balancesheet.accounts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.arathi.balancesheet.R;

import java.util.List;

/**
 * Created by arathi on 8/3/17.
 * This file helps in listing up the individual accounts.
 */

class AccountDetailsAdapter extends BaseAdapter {

    private List<AccountDetails> accountDetailsList;
    private Context context;
    private int numItems;

    AccountDetailsAdapter(List<AccountDetails> items, Context context){
    accountDetailsList = items;
        this.context = context;
        if(items!= null){
        this.numItems = items.size();}
        else {
            this.numItems = 0;
        }
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
        AccountViewHolder viewHolder;
        if(convertView==null) {
            viewHolder = new AccountViewHolder();
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.account_details, parent, false);
            viewHolder.accountName = (TextView) convertView.findViewById(R.id.account_name);
            viewHolder.accountNumber = (TextView) convertView.findViewById(R.id.account_number);
            viewHolder.accountBalance = (TextView) convertView.findViewById(R.id.account_balance);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (AccountViewHolder) convertView.getTag();
        }
        viewHolder.accountName.setText(accountDetails.getAccountName());
        viewHolder.accountNumber.setText(accountDetails.getAccountNumber());
        String accBalance = Float.toString(accountDetails.getAccountBalance());
        viewHolder.accountBalance.setText(accBalance);

        return convertView;
    }

     private static class AccountViewHolder{
         private TextView accountName;
         private TextView accountNumber;
         private TextView accountBalance;
     }

}

