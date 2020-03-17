package com.example.microsftlogin.EditCvFragements;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.microsftlogin.R;
import com.example.microsftlogin.UserProjectsDatabase.UserProject;
import com.example.microsftlogin.UserProjectsDatabase.UserProjectViewModel;
import com.example.microsftlogin.Utils.SharedPrefrenceUtil;
import com.google.android.material.textfield.TextInputLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddUserPersonalProjects extends Fragment {
    int user_id = SharedPrefrenceUtil.getInstance(getActivity()).getIntValue(SharedPrefrenceUtil.CURRENT_USER_ID);
    private TextInputLayout addUserProjectName;
    private TextInputLayout addUserProjectTasks;
    private Button addUserProjectSubmit;
    private UserProjectViewModel userProjectViewModel;

    public AddUserPersonalProjects() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_user_personal_projects, container, false);
        addUserProjectName = view.findViewById(R.id.adduserprojects_jobtitle);
        addUserProjectTasks = view.findViewById(R.id.adduserprojects_description);
        addUserProjectSubmit = view.findViewById(R.id.adduserprojects_submit);

        userProjectViewModel = ViewModelProviders.of(getActivity()).get(UserProjectViewModel.class);

        if (getArguments() != null) {
            addUserProjectName.getEditText().setText(getArguments().getString("ProjectName"));
            addUserProjectTasks.getEditText().setText(getArguments().getString("ProjectTasks"));
        }

        addUserProjectSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int actionToPerform = 0;
                if (getArguments() != null) {
                    actionToPerform = getArguments().getInt("ActionToPerform");
                }
                String projectName = addUserProjectName.getEditText().getText().toString();
                String projectTasks = addUserProjectTasks.getEditText().getText().toString();
                int user_id = SharedPrefrenceUtil.getInstance(getActivity()).getIntValue(SharedPrefrenceUtil.CURRENT_USER_ID);

                if (actionToPerform == 2) {
                 if (!projectName.equals("") && !projectTasks.equals("")) {
                     final int userProjectIdReceived = getArguments().getInt("ProjectUserId");
                     UserProject userProjectToUpdate = new UserProject(userProjectIdReceived,projectName,projectTasks,user_id);
                     userProjectViewModel.update(userProjectToUpdate);
                     Toast.makeText(getActivity(),"Your Project Has Been Updated",Toast.LENGTH_LONG).show();
                     Navigation.findNavController(v).popBackStack();
                     Navigation.findNavController(v).navigate(R.id.userPersonalProjects);
                 } else {
                     Toast.makeText(getActivity(), "All Fields Need To Be Filled", Toast.LENGTH_SHORT).show();
                 }
                }
                else {
                    if (!projectName.equals("") && !projectTasks.equals("")) {
                        UserProject userProjectToAdd = new UserProject(projectName, projectTasks, user_id);
                        userProjectViewModel.insert(userProjectToAdd);
                        Toast.makeText(getActivity(), "Your Project Has Been Added", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getActivity(), "All Fields Need To Be Filled", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return view;
    }
}
