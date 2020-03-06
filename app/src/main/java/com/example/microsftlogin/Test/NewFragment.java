package com.example.microsftlogin.Test;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.microsftlogin.HomePage;
import com.example.microsftlogin.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewFragment extends Fragment {

    Button goback;

    public NewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new, container, false);
        goback = view.findViewById(R.id.gobackbutton);
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.createNavigateOnClickListener(R.id.editTodoFragment);
            }
        });

        return view;
    }

}
