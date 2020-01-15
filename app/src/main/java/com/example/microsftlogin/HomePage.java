package com.example.microsftlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.microsftlogin.Helpers.SharedPrefrenceHelper;

public class HomePage extends AppCompatActivity {

    SharedPrefrenceHelper sph = new SharedPrefrenceHelper();
    TextView account_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        account_name = findViewById(R.id.account_name);

        sph.mprefrences = getSharedPreferences(sph.getSharedfile(),MODE_PRIVATE);
        account_name.setText(sph.getSpString(sph.getPerson_name()));
    }

    public void logout_user(View view) {
        sph.setIslogin(false);
        sph.editSpBoolean(sph.getLogin_key(),sph.getisLogin());
        Intent logoutintent = new Intent(HomePage.this,MainActivity.class);
        startActivity(logoutintent);
    }

    public void delete_user(View view) {
        sph.deleteSpDetails();
        Intent logoutintent = new Intent(HomePage.this,MainActivity.class);
        startActivity(logoutintent);
    }
}
