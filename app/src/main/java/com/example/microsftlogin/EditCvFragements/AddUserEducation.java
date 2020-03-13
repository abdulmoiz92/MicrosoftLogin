package com.example.microsftlogin.EditCvFragements;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.microsftlogin.Helpers.WorkPickerFragment;
import com.example.microsftlogin.R;
import com.example.microsftlogin.UserEducationDatabase.UserEducation;
import com.example.microsftlogin.UserEducationDatabase.UserEducationViewModel;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddUserEducation extends Fragment {
    private TextInputLayout addusereducationCourseName;
    private TextInputLayout addusereducationSchool;
    private Button addusereducationStudiedFrombtn;
    private Button addusereducationStudiedTillbtn;
    private TextView addusereducationStudiedFromDate;
    private TextView addusereducationStudiedTillDate;
    private TextInputLayout addusereducationSchoolAddress;
    private TextInputLayout addusereducationSubCourses;
    private Button addusereducationSubmitbtn;

    private List<UserEducation> userEducationList = new ArrayList<>();

    private UserEducationViewModel userEducationViewModel;

    public AddUserEducation() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_user_education, container, false);
        addusereducationCourseName = view.findViewById(R.id.addusereducation_coursename);
        addusereducationSchool = view.findViewById(R.id.addusereducation_school);
        addusereducationStudiedFrombtn = view.findViewById(R.id.addusereducation_learnedfrom);
        addusereducationStudiedTillbtn = view.findViewById(R.id.addusereducation_learnedtill);
        addusereducationStudiedFromDate = view.findViewById(R.id.addusereducation_learnedfromdate);
        addusereducationStudiedTillDate = view.findViewById(R.id.addusereducation_learnedtilldate);
        addusereducationSchoolAddress = view.findViewById(R.id.addusereducation_schoolAddress);
        addusereducationSubCourses = view.findViewById(R.id.addusereducation_description);
        addusereducationSubmitbtn = view.findViewById(R.id.addusereducation_submit);

        userEducationViewModel = ViewModelProviders.of(getActivity()).get(UserEducationViewModel.class);
        userEducationViewModel.getAllUserEducations().observe(getActivity(), new Observer<List<UserEducation>>() {
            @Override
            public void onChanged(List<UserEducation> userEducations) {
                userEducationList = userEducations;
            }
        });

        addusereducationStudiedFrombtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Clicked",Toast.LENGTH_LONG).show();
                WorkPickerFragment datePicker = new WorkPickerFragment(addusereducationStudiedFromDate,null);
                datePicker.show(getFragmentManager(),"studiedFrom");
            }
        });

        addusereducationStudiedTillbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String minDateText = addusereducationStudiedFromDate.getText().toString();
                WorkPickerFragment datePicker = new WorkPickerFragment(addusereducationStudiedTillDate,minDateText);
                datePicker.show(getFragmentManager(),"studiedTill");
            }
        });

        addusereducationSubmitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String courseName = addusereducationCourseName.getEditText().getText().toString();
                String schoolName = addusereducationSchool.getEditText().getText().toString();
                String studiedFrom = addusereducationStudiedFromDate.getText().toString();
                String studiedTill = addusereducationStudiedTillDate.getText().toString();
                String schoolAddress = addusereducationSchoolAddress.getEditText().getText().toString();
                String subCourses = addusereducationSubCourses.getEditText().toString();

                UserEducation newUserEducation = new UserEducation(courseName,schoolName,studiedFrom,studiedTill,
                        schoolAddress,subCourses);

                userEducationViewModel.insert(newUserEducation);
                Toast.makeText(getActivity(),"User Education Added",Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }
}
