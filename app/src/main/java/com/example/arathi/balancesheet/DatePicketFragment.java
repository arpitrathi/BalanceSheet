package com.example.arathi.balancesheet;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

/**
 * Created by arathi on 8/4/17.
 */

public class DatePicketFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        return null;
    }

        @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    StringBuilder str = new StringBuilder();
            str.append(year).append("/").append(month+1).append("/").append(dayOfMonth);

    }
}
