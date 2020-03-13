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
public class UserEducation extends Fragment {
    private FloatingActionButton addUserEducation;
    private NavController navController;

    public UserEducation() {
        // Required empty public
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_education, container, false);
        addUserEducation = view.findViewById(R.id.add_usereducation_btn);
        navController = Navigation.findNavController(getActivity(), R.id.fragment);
        addUserEducation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.addUserEducation);
            }
        });
        return view;
    }
}
