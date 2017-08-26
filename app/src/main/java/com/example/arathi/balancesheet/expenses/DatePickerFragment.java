package com.example.arathi.balancesheet.expenses;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

import static com.example.arathi.balancesheet.expenses.ExpenseEdit.getChosenDate;

//import static com.example.arathi.balancesheet.Expense.ExpenseEdit.showDate;

/**
 * Created by arathi on 8/12/17.
 * This file is used to pick date for expense.
 */

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{


    @Override
    public Dialog onCreateDialog(Bundle savedBundleInstance){
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        // Create a new instance of DatePickerDialog and return it
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        return datePickerDialog;

    }



    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
         getChosenDate( dayOfMonth, month+1, year);

    }

}
