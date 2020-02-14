package com.example.microsftlogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.microsftlogin.Helpers.SharedPrefrenceHelper;
import com.example.microsftlogin.Utils.SharedPrefrenceUtil;

import static com.example.microsftlogin.Utils.SharedPrefrenceUtil.IS_FIRST_TIME_LAUNCH;
import static com.example.microsftlogin.Utils.SharedPrefrenceUtil.IS_LOGIN;
import static com.example.microsftlogin.Utils.SharedPrefrenceUtil.USER_EMAIL;
import static com.example.microsftlogin.Utils.SharedPrefrenceUtil.USER_PASSWORD;

public class MainActivity extends AppCompatActivity {

    SharedPrefrenceHelper sph = new SharedPrefrenceHelper();
    //private static final int TEXT_REQUEST = 1;
    private TextView login_email;
    private EditText login_password;
    private String email_pref;
    private String password_pref;
    private TextView login_error_message;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!SharedPrefrenceUtil.getInstance(getApplicationContext()).getBooleanValue(IS_FIRST_TIME_LAUNCH)) {
            Intent splashScreen = new Intent(this, SplashScreen.class);
            startActivity(splashScreen);
        }

        else if (SharedPrefrenceUtil.getInstance(getApplicationContext()).getBooleanValue(IS_LOGIN)) {
            Intent homePage = new Intent(MainActivity.this, HomePage.class);
            //homePage.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homePage);
        }

        else {

            login_email = findViewById(R.id.email);
            login_password = findViewById(R.id.password);
            login_error_message = findViewById(R.id.login_error);

            Bundle bundle = getIntent().getExtras();
            if (null != bundle) {

                Toast.makeText(this, "Registered Successfully", Toast.LENGTH_LONG).show();
                login_email.setText(SharedPrefrenceUtil.getInstance(getApplicationContext()).getStringValue(USER_EMAIL));
                login_password.setText(SharedPrefrenceUtil.getInstance(getApplicationContext()).getStringValue(USER_PASSWORD));

            }

            email_pref = SharedPrefrenceUtil.getInstance(getApplicationContext()).getStringValue(USER_EMAIL);
            password_pref = SharedPrefrenceUtil.getInstance(getApplicationContext()).getStringValue(USER_PASSWORD);
        }
    }

    public void direct_to_register(View view) {
        Intent intent = new Intent(MainActivity.this, Register.class);
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
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(this, "Server Is Busy Please Try Again Later", Toast.LENGTH_SHORT).show();
        }
    }

    public void check_login_details(View view) {
        String email = login_email.getText().toString();
        String password = login_password.getText().toString();
        if (!email.equals("") && email.equals(email_pref) && !password.equals("") && password.equals(password_pref)) {
            Intent loginintent = new Intent(MainActivity.this, HomePage.class);
            SharedPrefrenceUtil.getInstance(getApplicationContext()).saveValue(IS_LOGIN,true);
            loginintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(loginintent);
        } else {
            login_error_message.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("IsUserLogin", sph.getisLogin());
    }
}
