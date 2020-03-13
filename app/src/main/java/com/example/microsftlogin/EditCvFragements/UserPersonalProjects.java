package com.example.microsftlogin.EditCvFragements;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.microsftlogin.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserPersonalProjects extends Fragment {
    private FloatingActionButton addUserProjects;
    private NavController navController;

    public UserPersonalProjects() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_personal_projects, container, false);
        addUserProjects = view.findViewById(R.id.add_userprojects_btn);
        navController = Navigation.findNavController(getActivity(), R.id.fragment);
        addUserProjects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.addUserPersonalProjects);
            }
        });
        return view;
    }
}
