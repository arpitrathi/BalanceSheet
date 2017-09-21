package com.example.arathi.balancesheet.expenses;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.arathi.balancesheet.R;

import java.util.List;
import java.util.Locale;

/**
 * Created by arathi on 8/3/17.
 * This file is used in listing the individual expense.
 */

public class ExpenseDetailAdapter extends BaseAdapter {

    private List<ExpenseDetail> expenseDetailList;
    private Context context;
    private int numItems;

    public ExpenseDetailAdapter(List<ExpenseDetail> items, Context context){
        expenseDetailList = items;
        this.context = context;
        numItems = items.size();
    }


    @Override
    public int getCount() {
        return numItems;
    }

    @Override
    public Object getItem(int position) {
        return expenseDetailList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ExpenseViewHolder mViewHolder ;
        final ExpenseDetail expenseDetail = expenseDetailList.get(position);
        if(convertView==null) {

            mViewHolder = new ExpenseViewHolder();
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.expense_details, parent, false);

            mViewHolder.expenseName = (TextView) convertView.findViewById(R.id.expenseName);
            mViewHolder.expenseDate = (TextView) convertView.findViewById(R.id.expenseDate);
            mViewHolder.expenseAmount = (TextView) convertView.findViewById(R.id.expenseAmount);
            mViewHolder.expenseCategory = (TextView) convertView.findViewById(R.id.expenseCategory);
            mViewHolder.expenseAccount = (TextView) convertView.findViewById(R.id.expenseAccount);

            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ExpenseViewHolder) convertView.getTag();
        }
        mViewHolder.expenseName.setText(expenseDetail.getExpenseName());
        mViewHolder.expenseAccount.setText(expenseDetail.getAccountName());
        //mViewHolder.expenseAccount.setVisibility(View.INVISIBLE);
        mViewHolder.expenseDate.setText(expenseDetail.getExpenseDate());
        mViewHolder.expenseAmount.setText(String.format(Locale.getDefault(),"%.2f",expenseDetail.getAmount()));
        mViewHolder.expenseCategory.setText(expenseDetail.getExpenseCategory());
        mViewHolder.expenseCategory.setVisibility(View.INVISIBLE);
        mViewHolder.expenseAmount.setTextSize(20);
        int incOrNot = expenseDetail.getCheckIncomeOrExpense();
        if (incOrNot==1){
            mViewHolder.expenseAmount.setTextColor(Color.GREEN);
        } else {
            mViewHolder.expenseAmount.setTextColor(Color.RED);
        }


        return convertView;
    }

    private static class ExpenseViewHolder {
        private TextView expenseName;
        private TextView expenseDate;
        private TextView expenseAmount;
        private TextView expenseCategory;
        private TextView expenseAccount;
    }
}
