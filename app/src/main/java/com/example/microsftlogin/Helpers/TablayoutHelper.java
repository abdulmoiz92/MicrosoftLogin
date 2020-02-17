package com.example.microsftlogin.Helpers;

import android.app.Activity;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.example.microsftlogin.PagerAdapter;
import com.example.microsftlogin.R;
import com.google.android.material.tabs.TabLayout;

public class TablayoutHelper {

    public void Tablayout(TabLayout tabLayout, int drawable, String text) {
       TabLayout.Tab firstTab = tabLayout.newTab();
        firstTab.setIcon(drawable);
        firstTab.setText(text);
        tabLayout.addTab(firstTab);
    }

    public void Addpager(final ViewPager viewPager, PagerAdapter adapter, TabLayout tabLayout) {
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
