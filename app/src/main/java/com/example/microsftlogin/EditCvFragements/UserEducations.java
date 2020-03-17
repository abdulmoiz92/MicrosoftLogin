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

import com.example.microsftlogin.Adapter.UserEducationAdapter;
import com.example.microsftlogin.R;
import com.example.microsftlogin.UserDatabase.UserViewModel;
import com.example.microsftlogin.UserEducationDatabase.UserEducation;
import com.example.microsftlogin.UserEducationDatabase.UserEducationViewModel;
import com.example.microsftlogin.Utils.SharedPrefrenceUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserEducations extends Fragment {
    private FloatingActionButton addUserEducation;
    private NavController navController;
    private UserViewModel userViewModel;
    private UserEducationViewModel userEducationViewModel;
    private RecyclerView mRecyclerView;
    private UserEducationAdapter mAdapter;
    private List<UserEducation> userEducationList = new ArrayList<>();
    int user_id = SharedPrefrenceUtil.getInstance(getActivity()).getIntValue(SharedPrefrenceUtil.CURRENT_USER_ID);



    public UserEducations() {
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

        userViewModel = ViewModelProviders.of(getActivity()).get(UserViewModel.class);
        userEducationViewModel = ViewModelProviders.of(getActivity()).get(UserEducationViewModel.class);
        userEducationList = userViewModel.findUserWithEducation(user_id).get(0).getUserEducations();
        mAdapter = new UserEducationAdapter(getActivity(),userEducationList,userEducationViewModel);
        mRecyclerView = view.findViewById(R.id.usereducation_recyclerView);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        return view;
    }
}
