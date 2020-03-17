package com.example.microsftlogin.EditCvFragements;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.microsftlogin.Adapter.UserExperienceAdapter;
import com.example.microsftlogin.Helpers.WorkPickerFragment;
import com.example.microsftlogin.R;
import com.example.microsftlogin.UserDatabase.UserViewModel;
import com.example.microsftlogin.UserExperienceDatabase.UserExperience;
import com.example.microsftlogin.UserExperienceDatabase.UserExperienceViewModel;
import com.example.microsftlogin.Utils.SharedPrefrenceUtil;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddUserExperience extends Fragment {
    int user_id = SharedPrefrenceUtil.getInstance(getActivity()).getIntValue(SharedPrefrenceUtil.CURRENT_USER_ID);
    private TextInputLayout adduserexperienceJobTitle;
    private TextInputLayout adduserexperienceCompanyName;
    private TextView adduserexperienceWorkedFromdate;
    private TextView adduserexperienceWorkedTilldate;
    private TextInputLayout adduserexperienceCompanyAddress;
    private TextInputLayout adduserexperienceTasksPerformed;
    private Button adduserexperienceWorkedFrom;
    private Button adduserexperienceWorkedTill;
    private Button adduserexperienceSubmit;
    private UserExperienceViewModel userExperienceViewModel;
    private UserViewModel userViewModel;
    private List<UserExperience> allUserExpiriences = new ArrayList<>();


    public AddUserExperience() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_user_experience, container, false);
        adduserexperienceJobTitle = view.findViewById(R.id.adduserexperience_jobtitle);
        adduserexperienceCompanyName = view.findViewById(R.id.adduserexperience_companyname);
        adduserexperienceWorkedFromdate = view.findViewById(R.id.adduserexperience_workedfromdate);
        adduserexperienceWorkedTilldate = view.findViewById(R.id.adduserexperience_workedtilldate);
        adduserexperienceCompanyAddress = view.findViewById(R.id.adduserexperience_companyaddress);
        adduserexperienceTasksPerformed = view.findViewById(R.id.adduserexperience_description);
        adduserexperienceWorkedFrom = view.findViewById(R.id.adduserexperience_workedfrom);
        adduserexperienceWorkedTill = view.findViewById(R.id.adduserexperience_workedtill);
        adduserexperienceSubmit = view.findViewById(R.id.adduserexperience_submit);


        int userexperienceIdReceived;

        userExperienceViewModel = ViewModelProviders.of(getActivity()).get(UserExperienceViewModel.class);
        userViewModel = ViewModelProviders.of(getActivity()).get(UserViewModel.class);
        allUserExpiriences = userViewModel.findUserWithExperiences(user_id).get(0).getUserExperiences();

        adduserexperienceWorkedFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WorkPickerFragment datePicker = new WorkPickerFragment(adduserexperienceWorkedFromdate, null);
                datePicker.show(getFragmentManager(), "datePickerFrom");
            }
        });

        adduserexperienceWorkedTill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String minDateText = adduserexperienceWorkedFromdate.getText().toString();
                WorkPickerFragment datePicker = new WorkPickerFragment(adduserexperienceWorkedTilldate, minDateText);
                datePicker.show(getFragmentManager(), "datePickerTill");
            }
        });


        if (getArguments() != null) {
            String jobTitleReceived = getArguments().getString("JobTitle");
            String companyNameReceived = getArguments().getString("CompanyName");
            String workedFromReceived = getArguments().getString("WorkedFrom");
            String workedTillReceived = getArguments().getString("WorkedTill");
            String companyAddressReceived = getArguments().getString("CompanyAddress");
            String tasksPerformedReceived = getArguments().getString("TasksPerformed");
            adduserexperienceJobTitle.getEditText().setText(jobTitleReceived);
            adduserexperienceCompanyName.getEditText().setText(companyNameReceived);
            adduserexperienceWorkedFromdate.setText(workedFromReceived);
            adduserexperienceWorkedTilldate.setText(workedTillReceived);
            adduserexperienceCompanyAddress.getEditText().setText(companyAddressReceived);
            adduserexperienceTasksPerformed.getEditText().setText(tasksPerformedReceived);
        }


        adduserexperienceSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int actionToPerform = 0;
                if (getArguments() != null) {
                    actionToPerform = getArguments().getInt("ActionToPerform");
                }
                String jobTitle = adduserexperienceJobTitle.getEditText().getText().toString();
                String companyName = adduserexperienceCompanyName.getEditText().getText().toString();
                String workedFromDate = adduserexperienceWorkedFromdate.getText().toString();
                String workedTillDate = adduserexperienceWorkedTilldate.getText().toString();
                String companyAddress = adduserexperienceCompanyAddress.getEditText().getText().toString();
                String tasksPerformed = adduserexperienceTasksPerformed.getEditText().getText().toString();

                if (actionToPerform == 2) {
                    if (!jobTitle.equals("") && !companyName.equals("") && !workedFromDate.equals("") && !workedTillDate.equals("")
                            && !companyAddress.equals("") && !tasksPerformed.equals("")) {
                        final int userexperienceIdReceived = getArguments().getInt("UserExperienceId");
                        UserExperience userToBeUpdated = new UserExperience(userexperienceIdReceived, jobTitle, companyName,
                                workedFromDate, workedTillDate, companyAddress, tasksPerformed, user_id);
                        userExperienceViewModel.update(userToBeUpdated);
                        Toast.makeText(getActivity(), "Experience Information Has Been Updated", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getActivity(), "All Fields Are Required", Toast.LENGTH_LONG).show();
                    }
                } else {

                    int user_id = SharedPrefrenceUtil.getInstance(getActivity()).getIntValue(SharedPrefrenceUtil.CURRENT_USER_ID);

                    if (!jobTitle.equals("") && !companyName.equals("") && !workedFromDate.equals("") && !workedTillDate.equals("")
                            && !companyAddress.equals("") && !tasksPerformed.equals("")) {

                        UserExperience newUserExperience = new UserExperience(jobTitle, companyName, workedFromDate, workedTillDate,
                                companyAddress, tasksPerformed, user_id);
                        userExperienceViewModel.insert(newUserExperience);
                    } else {
                        Toast.makeText(getActivity(), "All Fields Are Required", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        return view;
    }
}
