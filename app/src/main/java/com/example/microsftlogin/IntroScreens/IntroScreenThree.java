package com.example.microsftlogin.IntroScreens;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.microsftlogin.StartUpActivities.LandingPage;
import com.example.microsftlogin.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class IntroScreenThree extends Fragment {

    ViewPager intro_pager;
    Button intro_three_btn_next;
    Button intro_three_btn_back;

    public IntroScreenThree() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_intro_screen_three, container, false);

        intro_pager = getActivity().findViewById(R.id.intro_pager);
        intro_three_btn_next = view.findViewById(R.id.intro_three_finish);
        intro_three_btn_back = view.findViewById(R.id.intro_three_back);

            intro_three_btn_next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent landingpage = new Intent(getActivity(), LandingPage.class);
                    startActivity(landingpage);
                    getActivity().finish();
                }
            });

            intro_three_btn_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intro_pager.setCurrentItem(1);
                }
            });

        return view;
    }

}
