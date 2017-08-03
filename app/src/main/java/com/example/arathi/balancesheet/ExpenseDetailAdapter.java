package com.example.arathi.balancesheet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

/**
 * Created by arathi on 8/3/17.
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
        final RelativeLayout expenseLayout = (RelativeLayout) LayoutInflater.from(context).inflate(
                R.layout.expense_details, parent, false);
        final ExpenseDetail expenseDetail = expenseDetailList.get(position);
        final Calendar c = Calendar.getInstance();
        int date = c.get(Calendar.DATE);
        int month = c.get(Calendar.MONTH);
        StringBuilder str = new StringBuilder();
        str.append(date);
        str.append("/");
        str.append(month+1);

        TextView expenseName = (TextView)expenseLayout.findViewById(R.id.expenseName);
        TextView expenseDate = (TextView)expenseLayout.findViewById(R.id.expenseDate);
        TextView expenseAmount = (TextView)expenseLayout.findViewById(R.id.expenseAmount);
        TextView expenseCategory = (TextView)expenseLayout.findViewById(R.id.expenseCategory);
        TextView expenseMode = (TextView)expenseLayout.findViewById(R.id.expenseMode);

        expenseName.setText(expenseDetail.getExpenseName());
        //expenseMode.setText(expenseDetail.getExpenseMode());
        expenseDate.setText(expenseDetail.getExpenseDate());
        expenseAmount.setText(Float.toString(expenseDetail.getAmount()));
        //expenseCategory.setText(expenseDetail.getExpenseCategory());



        return expenseLayout;
    }
}
