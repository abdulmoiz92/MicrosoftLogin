package com.example.microsftlogin.Helpers;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.microsftlogin.Adapter.Todo;
import com.example.microsftlogin.HomePage;
import com.example.microsftlogin.MainActivity;
import com.example.microsftlogin.R;
import com.example.microsftlogin.dashboardsActivities.TodolistFragment;

import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class DatepickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    public interface InterfaceCommunicator {
        void sendRequestCode(int Code);
    }

    public DatepickerFragment() {
        // Required empty public constructor
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker.
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it.
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, year, month, day);
        dialog.getDatePicker().setMinDate(new Date().getTime());
        return dialog;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
       TextView date = getActivity().findViewById(R.id.addtodo_date);
        String month_string = Integer.toString(month+1);
        String day_string = Integer.toString(dayOfMonth);
        String year_string = Integer.toString(year);
        String dateMessage = (month_string +
                "/" + day_string + "/" + year_string);
        date.setVisibility(View.VISIBLE);
        date.setText(dateMessage);
    }
}
