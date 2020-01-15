package com.example.microsftlogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.microsftlogin.Helpers.SharedPrefrenceHelper;

public class MainActivity extends AppCompatActivity {

    SharedPrefrenceHelper sph = new SharedPrefrenceHelper();
    private static final int TEXT_REQUEST = 1;
    private TextView login_email;
    private EditText login_password;
    private String email_pref;
    private String password_pref;
    private TextView login_error_message;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sph.mprefrences = getSharedPreferences(sph.getSharedfile(),MODE_PRIVATE);
        login_email = findViewById(R.id.email);
        login_password = findViewById(R.id.password);
        login_error_message = findViewById(R.id.login_error);

        Bundle bundle = getIntent().getExtras();
        if(null != bundle){

            Toast.makeText(this, "Registered Successfully", Toast.LENGTH_LONG).show();
            sph.getSpBoolean(sph.getLogin_key(),false);
            login_email.setText(sph.getSpString(sph.getEmail_Key()));
            login_password.setText(sph.getSpString(sph.getPassword_Key()));

        }

        email_pref = sph.getSpString(sph.getEmail_Key());
        password_pref = sph.getSpString(sph.getPassword_Key());

    }

    public void direct_to_register(View view) {
        Intent intent = new Intent(MainActivity.this,Register.class);
        startActivity(intent);
        //startActivityForResult(intent,TEXT_REQUEST);
    }
/*
    @Override
    public void onActivityResult(int request_code, int result_code, Intent data) {
        super.onActivityResult(request_code,result_code,data);
        if (request_code == TEXT_REQUEST) {
            if (result_code == RESULT_OK) {
                String email = data.getStringExtra(Register.REGISTER);
                Toast.makeText(this, "Registered Successfully", Toast.LENGTH_LONG).show();
                login_email.setText(email);
            }
        }
    }*/

    public void openlogowebsite(View view) {
        String Url = "https://www.microsoft.com/";
        Uri webpage = Uri.parse(Url);
        Intent intent = new Intent(Intent.ACTION_VIEW,webpage);
        if (intent.resolveActivity(getPackageManager()) !=null ) {
            startActivity(intent);
        } else {
            Toast.makeText(this,"Server Is Busy Please Try Again Later",Toast.LENGTH_SHORT).show();
        }
    }

    public void check_login_details(View view) {
        String email = login_email.getText().toString();
        String password = login_password.getText().toString();
        if (!email.equals("") && email.equals(email_pref) && !password.equals("") && password.equals(password_pref) ) {
            Intent loginintent = new Intent(MainActivity.this,HomePage.class);
            sph.setIslogin(true);
            sph.editSpBoolean(sph.getLogin_key(), sph.getisLogin());
            startActivity(loginintent);
        } else {
            login_error_message.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("IsUserLogin",sph.getisLogin());
    }
}
