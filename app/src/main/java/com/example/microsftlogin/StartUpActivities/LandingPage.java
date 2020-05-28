package com.example.microsftlogin.StartUpActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.microsftlogin.R;
import com.example.microsftlogin.Utils.SharedPrefrenceUtil;

import static com.example.microsftlogin.Utils.SharedPrefrenceUtil.IS_FIRST_TIME_LAUNCH;

public class LandingPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        SharedPrefrenceUtil.getInstance(getApplicationContext()).saveValue(IS_FIRST_TIME_LAUNCH,true);

        Button landing_login = findViewById(R.id.landing_login);
        landing_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginintent = new Intent(LandingPage.this,MainActivity.class);
                startActivity(loginintent);
                finish();
            }
        });

        Button landing_signup = findViewById(R.id.landing_signup);
        landing_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signupintent = new Intent(LandingPage.this,Register.class);
                startActivity(signupintent);
                finish();
            }
        });
    }
}
