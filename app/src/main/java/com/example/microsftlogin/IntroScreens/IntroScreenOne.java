package com.example.microsftlogin.IntroScreens;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.microsftlogin.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class IntroScreenOne extends Fragment {

    Button intro_one_btn_next;
    ViewPager intro_pager;

    public IntroScreenOne() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_intro_screen_one, container, false);

        intro_pager = getActivity().findViewById(R.id.intro_pager);
        intro_one_btn_next = view.findViewById(R.id.intro_one_next);

            intro_one_btn_next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intro_pager.setCurrentItem(1);
                }
            });

        return view;
    }

}
