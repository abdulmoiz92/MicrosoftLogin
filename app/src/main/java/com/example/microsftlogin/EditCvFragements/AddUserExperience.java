package com.example.microsftlogin.EditCvFragements;

import android.content.Context;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.microsftlogin.Adapter.UserExperienceAdapter;
import com.example.microsftlogin.Helpers.WorkPickerFragment;
import com.example.microsftlogin.R;
import com.example.microsftlogin.UserDatabase.UserViewModel;
import com.example.microsftlogin.UserExperienceDatabase.UserExperience;
import com.example.microsftlogin.UserExperienceDatabase.UserExperienceViewModel;
import com.example.microsftlogin.Utils.SharedPrefrenceUtil;
import com.google.android.gms.tasks.OnCompleteListener;
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
public class AddUserExperience extends Fragment {
    String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
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
    private ProgressBar adduserexperienceProgress;
    private Boolean connected = false;
    private List<UserExperience> allUserExpiriences = new ArrayList<>();


    public AddUserExperience() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_add_user_experience, container, false);
        adduserexperienceJobTitle = view.findViewById(R.id.adduserexperience_jobtitle);
        adduserexperienceCompanyName = view.findViewById(R.id.adduserexperience_companyname);
        adduserexperienceWorkedFromdate = view.findViewById(R.id.adduserexperience_workedfromdate);
        adduserexperienceWorkedTilldate = view.findViewById(R.id.adduserexperience_workedtilldate);
        adduserexperienceCompanyAddress = view.findViewById(R.id.adduserexperience_companyaddress);
        adduserexperienceTasksPerformed = view.findViewById(R.id.adduserexperience_description);
        adduserexperienceWorkedFrom = view.findViewById(R.id.adduserexperience_workedfrom);
        adduserexperienceWorkedTill = view.findViewById(R.id.adduserexperience_workedtill);
        adduserexperienceSubmit = view.findViewById(R.id.adduserexperience_submit);
        adduserexperienceProgress = view.findViewById(R.id.adduserexperience_progress);

        final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        final DatabaseReference userexpRef = FirebaseDatabase.getInstance().getReference().child("users")
                .child(currentUser.getUid()).child("experiences");


        int userexperienceIdReceived;

        userExperienceViewModel = ViewModelProviders.of(getActivity()).get(UserExperienceViewModel.class);
        userViewModel = ViewModelProviders.of(getActivity()).get(UserViewModel.class);
        if (userViewModel.findUserWithExperiences(user_id).size() > 0) {
            allUserExpiriences = userViewModel.findUserWithExperiences(user_id).get(0).getUserExperiences();
        }

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
                ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
                if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                    //we are connected to a network
                    connected = true;
                } else {
                    connected = false;
                }
                if (connected == true) {
                    adduserexperienceSubmit.setVisibility(View.GONE);
                    adduserexperienceProgress.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.main_background),
                            PorterDuff.Mode.SRC_ATOP);
                    adduserexperienceProgress.setVisibility(View.VISIBLE);
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
                            final String userexperienceIdReceived = getArguments().getString("UserExperienceId");
                            final UserExperience userToBeUpdated = new UserExperience(userexperienceIdReceived, jobTitle, companyName,
                                    workedFromDate, workedTillDate, companyAddress, tasksPerformed, user_id);
                            userexpRef.child(userexperienceIdReceived).setValue(userToBeUpdated).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        userExperienceViewModel.update(userToBeUpdated);
                                        Toast.makeText(getActivity(), "Experience Information Has Been Updated", Toast.LENGTH_LONG).show();
                                        Navigation.findNavController(view).navigateUp();
                                    } else {
                                        adduserexperienceSubmit.setVisibility(View.VISIBLE);
                                        adduserexperienceProgress.setVisibility(View.GONE);
                                        Toast.makeText(getActivity(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(getActivity(), "All Fields Are Required", Toast.LENGTH_LONG).show();
                        }
                    } else {

                        String user_id = currentUser.getUid();

                        if (!jobTitle.equals("") && !companyName.equals("") && !workedFromDate.equals("") && !workedTillDate.equals("")
                                && !companyAddress.equals("") && !tasksPerformed.equals("")) {

                            DatabaseReference userexpRefPush = userexpRef.push();
                            final UserExperience newUserExperience = new UserExperience(userexpRefPush.getKey(), jobTitle, companyName, workedFromDate, workedTillDate,
                                    companyAddress, tasksPerformed, user_id);
                            userexpRefPush.setValue(newUserExperience).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        userExperienceViewModel.insert(newUserExperience);
                                        Toast.makeText(getActivity(), "Experience Has Been Added", Toast.LENGTH_SHORT).show();
                                        Navigation.findNavController(view).navigateUp();
                                    } else {
                                        adduserexperienceSubmit.setVisibility(View.VISIBLE);
                                        adduserexperienceProgress.setVisibility(View.GONE);
                                        Toast.makeText(getActivity(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            adduserexperienceSubmit.setVisibility(View.VISIBLE);
                            adduserexperienceProgress.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), "All Fields Are Required", Toast.LENGTH_LONG).show();
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