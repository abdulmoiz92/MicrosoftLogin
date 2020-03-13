package com.example.microsftlogin.Helpers;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.microsftlogin.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class WorkPickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    final Calendar c = Calendar.getInstance();
    private View view;
    private TextView dateTextView;
    private String minDate;

    public WorkPickerFragment(TextView dateTextView, String minDate) {
        // Required empty public constructor
        this.dateTextView = dateTextView;
        this.minDate = minDate;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
          onNewDate(view,year,month,dayOfMonth,dateTextView);
    }

    public interface InterfaceCommunicator {
            void sendRequestCode(int Code);
        }

    @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            // Use the current date as the default date in the picker.
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);



        // Create a new instance of DatePickerDialog and return it
            DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, year, month, day);
            setDateMin(dialog,minDate);
            return dialog;
        }


    public void onNewDate(DatePicker view, int year, int month, int dayOfMonth, TextView dateFrom) {
        String month_string = Integer.toString(month+1);
        String day_string = Integer.toString(dayOfMonth);
        String year_string = Integer.toString(year);
        final String dateMessage = (month_string +
                "/" + day_string + "/" + year_string);
        dateFrom.setText(dateMessage);
    }

    public void setDateMin(DatePickerDialog view,String minDate) {
        if (null != minDate) {
            Date minDateSet = null;
            try {
                minDateSet = new SimpleDateFormat("MM/dd/yyyy").parse(minDate);
                view.getDatePicker().setMinDate(minDateSet.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}
