package com.example.microsftlogin.EditCvFragements;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.microsftlogin.Helpers.DatepickerFragment;
import com.example.microsftlogin.Helpers.WorkPickerFragment;
import com.example.microsftlogin.R;
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

        adduserexperienceJobTitle.getEditText().setText("Testing");

        userExperienceViewModel = ViewModelProviders.of(getActivity()).get(UserExperienceViewModel.class);
        userExperienceViewModel.getAllUsersExperience().observe(getActivity(), new Observer<List<UserExperience>>() {
            @Override
            public void onChanged(List<UserExperience> userExperiences) {
                allUserExpiriences = userExperiences;
            }
        });

        adduserexperienceSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String jobTitle = adduserexperienceJobTitle.getEditText().getText().toString();
                String companyName = adduserexperienceCompanyName.getEditText().getText().toString();
                String workedFromDate = adduserexperienceWorkedFromdate.getText().toString();
                String workedTillDate = adduserexperienceWorkedTilldate.getText().toString();
                String companyAddress = adduserexperienceCompanyAddress.getEditText().getText().toString();
                String tasksPerformed = adduserexperienceTasksPerformed.getEditText().getText().toString();

                int user_id = SharedPrefrenceUtil.getInstance(getActivity()).getIntValue(SharedPrefrenceUtil.CURRENT_USER_ID);

                UserExperience newUserExperience = new UserExperience(jobTitle,companyName,workedFromDate,workedTillDate,
                        companyAddress,tasksPerformed,user_id);
                userExperienceViewModel.insert(newUserExperience);
            }
        });

        return view;
    }
}
