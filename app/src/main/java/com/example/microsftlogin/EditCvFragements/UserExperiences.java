package com.example.microsftlogin.EditCvFragements;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.microsftlogin.Adapter.UserExperienceAdapter;
import com.example.microsftlogin.R;
import com.example.microsftlogin.UserDatabase.UserViewModel;
import com.example.microsftlogin.UserDatabaseRelation.UserWithExperience;
import com.example.microsftlogin.UserExperienceDatabase.UserExperience;
import com.example.microsftlogin.UserExperienceDatabase.UserExperienceViewModel;
import com.example.microsftlogin.Utils.SharedPrefrenceUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserExperiences extends Fragment {
    private FloatingActionButton addUserExperience;
    private NavController navController;
    private UserViewModel userViewModel;
    private RecyclerView mRecyclerView;
    private UserExperienceAdapter mAdapter;
   // private List<UserWithExperience> userWithExperienceList = new ArrayList<>();
    private List<UserExperience> userExperienceList = new ArrayList<>();
    int user_id = SharedPrefrenceUtil.getInstance(getActivity()).getIntValue(SharedPrefrenceUtil.CURRENT_USER_ID);
    private UserExperienceViewModel userExperienceViewModel;

    public UserExperiences() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_experiences, container, false);
        addUserExperience = view.findViewById(R.id.add_userexperience_btn);
        navController = Navigation.findNavController(getActivity(), R.id.fragment);
        addUserExperience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.addUserExperience);
            }
        });

        userExperienceViewModel= ViewModelProviders.of(getActivity()).get(UserExperienceViewModel.class);
        userViewModel = ViewModelProviders.of(getActivity()).get(UserViewModel.class);
        userExperienceList = userViewModel.findUserWithExperiences(user_id).get(0).getUserExperiences();
        mAdapter = new UserExperienceAdapter(getActivity(),userExperienceList,userExperienceViewModel);
        mRecyclerView = view.findViewById(R.id.userexperience_recyclerView);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        return view;
    }
}
