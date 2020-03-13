package com.example.microsftlogin.Helpers;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.microsftlogin.R;

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
       TextView workFromDate= getActivity().findViewById(R.id.adduserexperience_workedfromdate);
       TextView workTillDate = getActivity().findViewById(R.id.adduserexperience_workedtilldate);
        String month_string = Integer.toString(month+1);
        String day_string = Integer.toString(dayOfMonth);
        String year_string = Integer.toString(year);
        String dateMessage = (month_string +
                "/" + day_string + "/" + year_string);
        date.setVisibility(View.VISIBLE);
            date.setText(dateMessage);
    }
}
