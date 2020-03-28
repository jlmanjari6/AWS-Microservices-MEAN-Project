package com.example.tourismapp.Helpers;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.tourismapp.R;

import java.text.DateFormat;
import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private int year, month, day;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar calender = Calendar.getInstance();
        this.year = calender.get(Calendar.YEAR);
        this.month = calender.get(Calendar.MONTH);
        this.day = calender.get(Calendar.DATE);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        return datePickerDialog;
    }

    @Override
    public void onDateSet(DatePicker view, int selYear, int selMonth, int selDayOfMonth) {
        //set selected date of birth
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, selYear);
        calendar.set(Calendar.MONTH, selMonth);
        calendar.set(Calendar.DAY_OF_MONTH, selDayOfMonth);

        EditText etDob = (EditText) getActivity().findViewById(R.id.etDob);
        etDob.setText((selMonth+1)+"/"+selDayOfMonth+"/"+selYear);
    }
}
