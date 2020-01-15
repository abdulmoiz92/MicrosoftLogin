package com.example.microsftlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.microsftlogin.Helpers.SharedPrefrenceHelper;

public class SplashScreen extends AppCompatActivity {

    Handler handler;
    SharedPrefrenceHelper sph = new SharedPrefrenceHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        sph.mprefrences = getSharedPreferences(sph.getSharedfile(),MODE_PRIVATE);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (sph.getSpBoolean(sph.getLogin_key(),false) == true) {
                    Intent homeintent = new Intent(SplashScreen.this,HomePage.class);
                    startActivity(homeintent);
                } else {
                    Intent loginintent = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(loginintent);
                }
            }
        },3000);
    }
}
