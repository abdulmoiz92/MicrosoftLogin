package com.example.microsftlogin.IntroScreens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.microsftlogin.R;

public class WelcomeActivity extends AppCompatActivity {

    ViewPager introviewpager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        introviewpager = findViewById(R.id.intro_pager);
        IntroPagerAdapter adapter = new IntroPagerAdapter(getSupportFragmentManager());
        introviewpager.setAdapter(adapter);
    }
}
