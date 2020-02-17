package com.example.microsftlogin.TabsFragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.microsftlogin.Helpers.TablayoutHelper;
import com.example.microsftlogin.PagerAdapter;
import com.example.microsftlogin.R;
import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class TablayoutFragment extends Fragment {


    public TablayoutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LayoutInflater lf = getActivity().getLayoutInflater();
        View view = lf.inflate(R.layout.fragment_tablayout, container, false);

        TabLayout tabLayout = view.findViewById(R.id.tabsLayout);

        TablayoutHelper tlh = new TablayoutHelper();

        tlh.Tablayout(tabLayout, R.drawable.ic_home_white_24dp, "Home");
        tlh.Tablayout(tabLayout, R.drawable.ic_person_white_24dp, "User");

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = view.findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter(getChildFragmentManager(), tabLayout.getTabCount());

        tlh.Addpager(viewPager, adapter, tabLayout);
        return view;
    }

}
