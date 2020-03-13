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

import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class WorkPickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    final Calendar c = Calendar.getInstance();
    private View view;

    public WorkPickerFragment() {
        // Required empty public constructor
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
            dialog.getDatePicker().setMinDate(new Date().getTime());
            return dialog;
        }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        final TextView workFromDate= getActivity().findViewById(R.id.adduserexperience_workedfromdate);
        TextView workTillDate = getActivity().findViewById(R.id.adduserexperience_workedtilldate);
        Button workFromBtn = getActivity().findViewById(R.id.adduserexperience_workedfrom);
        String month_string = Integer.toString(month+1);
        String day_string = Integer.toString(dayOfMonth);
        String year_string = Integer.toString(year);
        final String dateMessage = (month_string +
                "/" + day_string + "/" + year_string);
    }
}
