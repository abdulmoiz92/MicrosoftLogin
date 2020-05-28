package com.example.microsftlogin.EditCvFragements;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.microsftlogin.Helpers.WorkPickerFragment;
import com.example.microsftlogin.R;
import com.example.microsftlogin.UserDatabase.UserViewModel;
import com.example.microsftlogin.UserEducationDatabase.UserEducation;
import com.example.microsftlogin.UserEducationDatabase.UserEducationViewModel;
import com.example.microsftlogin.Utils.SharedPrefrenceUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    private ProgressBar addusereducationProgress;
    private Boolean connected = false;
    private UserViewModel userViewModel;

    private List<UserEducation> userEducationList = new ArrayList<>();

    private UserEducationViewModel userEducationViewModel;

    public AddUserEducation() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_add_user_education, container, false);
        addusereducationCourseName = view.findViewById(R.id.addusereducation_coursename);
        addusereducationSchool = view.findViewById(R.id.addusereducation_school);
        addusereducationStudiedFrombtn = view.findViewById(R.id.addusereducation_learnedfrom);
        addusereducationStudiedTillbtn = view.findViewById(R.id.addusereducation_learnedtill);
        addusereducationStudiedFromDate = view.findViewById(R.id.addusereducation_learnedfromdate);
        addusereducationStudiedTillDate = view.findViewById(R.id.addusereducation_learnedtilldate);
        addusereducationSchoolAddress = view.findViewById(R.id.addusereducation_schoolAddress);
        addusereducationSubCourses = view.findViewById(R.id.addusereducation_description);
        addusereducationSubmitbtn = view.findViewById(R.id.addusereducation_submit);
        addusereducationProgress = view.findViewById(R.id.addusereducation_progress);

        int userEducationIdReceived;

        userEducationViewModel = ViewModelProviders.of(getActivity()).get(UserEducationViewModel.class);
        userViewModel = ViewModelProviders.of(getActivity()).get(UserViewModel.class);

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

        if (getArguments() != null) {
            String courseNameReceived = getArguments().getString("CourseName");
            String schoolNameReceived = getArguments().getString("SchoolName");
            String studiedFromReceived = getArguments().getString("StudiedFrom");
            String studiedTillReceived = getArguments().getString("StudiedTill");
            String schoolAddressReceived = getArguments().getString("SchoolAddress");
            String subCoursesReceived = getArguments().getString("SubCourses");
            addusereducationCourseName.getEditText().setText(courseNameReceived);
            addusereducationSchool.getEditText().setText(schoolNameReceived);
            addusereducationStudiedFromDate.setText(studiedFromReceived);
            addusereducationStudiedTillDate.setText(studiedTillReceived);
            addusereducationSchoolAddress.getEditText().setText(schoolAddressReceived);
            addusereducationSubCourses.getEditText().setText(subCoursesReceived);
        }

        addusereducationSubmitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                    //we are connected to a network
                    connected = true;
                } else {
                    connected = false;
                }

                if (connected == true) {
                    addusereducationSubmitbtn.setVisibility(View.GONE);
                    addusereducationProgress.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.main_background),
                            android.graphics.PorterDuff.Mode.SRC_ATOP);
                    addusereducationProgress.setVisibility(View.VISIBLE);
                    int actionToPerform = 0;
                    if (getArguments() != null) {
                        actionToPerform = getArguments().getInt("ActionToPerform");
                    }
                    String courseName = addusereducationCourseName.getEditText().getText().toString();
                    String schoolName = addusereducationSchool.getEditText().getText().toString();
                    String studiedFrom = addusereducationStudiedFromDate.getText().toString();
                    String studiedTill = addusereducationStudiedTillDate.getText().toString();
                    String schoolAddress = addusereducationSchoolAddress.getEditText().getText().toString();
                    String subCourses = addusereducationSubCourses.getEditText().getText().toString();

                    String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();


                    if (actionToPerform == 2) {
                        if (!courseName.equals("") && !schoolName.equals("") && !studiedFrom.equals("") &&
                                !studiedFrom.equals("") && !schoolAddress.equals("") && !subCourses.equals("")) {
                            final String userEducationIdReceived = getArguments().getString("UserEducationId");
                            DatabaseReference refEduUpdate = FirebaseDatabase.getInstance().getReference().child("users").
                                    child(user_id).child("educations");
                            final UserEducation userEducationToBeUpdated = new UserEducation(userEducationIdReceived, courseName, schoolName
                                    , studiedFrom, studiedTill, schoolAddress, subCourses, user_id);
                            refEduUpdate.child(userEducationIdReceived).setValue(userEducationToBeUpdated).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        userEducationViewModel.update(userEducationToBeUpdated);
                                        Toast.makeText(getActivity(), "Your Education Is Been Updated", Toast.LENGTH_LONG).show();
                                        Navigation.findNavController(view).navigateUp();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    addusereducationSubmitbtn.setVisibility(View.VISIBLE);
                                    addusereducationProgress.setVisibility(View.GONE);
                                    Toast.makeText(getActivity(), "Something Went Wrong Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            addusereducationSubmitbtn.setVisibility(View.VISIBLE);
                            addusereducationProgress.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), "All Fields Need To Be Filled", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        if (!courseName.equals("") && !schoolName.equals("") && !studiedFrom.equals("") &&
                                !studiedFrom.equals("") && !schoolAddress.equals("") && !subCourses.equals("")) {
                            DatabaseReference refEdu = FirebaseDatabase.getInstance().getReference().child("users").
                                    child(user_id).child("educations").push();
                            final UserEducation newUserEducation = new UserEducation(refEdu.getKey(), courseName, schoolName, studiedFrom, studiedTill,
                                    schoolAddress, subCourses, user_id);
                            refEdu.setValue(newUserEducation).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        userEducationViewModel.insert(newUserEducation);
                                        Toast.makeText(getActivity(), "Your Education Has Been Added", Toast.LENGTH_LONG).show();
                                        Navigation.findNavController(view).navigateUp();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    addusereducationSubmitbtn.setVisibility(View.VISIBLE);
                                    addusereducationProgress.setVisibility(View.GONE);
                                    Toast.makeText(getActivity(), "Something Went Wrong Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                                }
                            });

                        } else {
                            Toast.makeText(getActivity(), "All Fields To Be Filled", Toast.LENGTH_LONG).show();
                        }
                    }
                } else {
                    Toast.makeText(getActivity(), "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}
