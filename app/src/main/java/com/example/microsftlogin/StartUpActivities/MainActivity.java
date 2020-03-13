package com.example.microsftlogin.StartUpActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.microsftlogin.Helpers.SharedPrefrenceHelper;
import com.example.microsftlogin.HomePage;
import com.example.microsftlogin.R;
import com.example.microsftlogin.UserDatabase.User;
import com.example.microsftlogin.UserDatabase.UserViewModel;
import com.example.microsftlogin.Utils.SharedPrefrenceUtil;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import static com.example.microsftlogin.Utils.SharedPrefrenceUtil.CURRENT_USER_ID;
import static com.example.microsftlogin.Utils.SharedPrefrenceUtil.IS_FIRST_TIME_LAUNCH;
import static com.example.microsftlogin.Utils.SharedPrefrenceUtil.IS_LOGIN;

public class MainActivity extends AppCompatActivity {

    SharedPrefrenceHelper sph = new SharedPrefrenceHelper();
    //private static final int TEXT_REQUEST = 1;
    private TextInputLayout login_email;
    private TextInputLayout login_password;
    private String email_pref;
    private String password_pref;
    private TextView login_error_message;
    private UserViewModel userViewModel;
    private List<User> allUsers = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.getuAllUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                allUsers = users;
            }
        });

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

                login_email.getEditText().setText(bundle.getString("email"));
                login_password.getEditText().setText(bundle.getString("password"));

            }

           /* email_pref = SharedPrefrenceUtil.getInstance(getApplicationContext()).getStringValue(USER_EMAIL);
            password_pref = SharedPrefrenceUtil.getInstance(getApplicationContext()).getStringValue(USER_PASSWORD); */
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
        String email = login_email.getEditText().getText().toString();
        String password = login_password.getEditText().getText().toString();
        boolean verifyEmail = false;
        boolean verifyPassword = false;
      /*  if (allUsers.size() > 0) {
           for (int i= 0; i <= allUsers.size() - 1; i++) {
               if (allUsers.get(i).getEmail().equals(email) && allUsers.get(i).getPassword().equals(password)) {
                   verifyEmail = true;
                   verifyPassword = true;
                   SharedPrefrenceUtil.getInstance(getApplicationContext()).saveValue(CURRENT_USER_ID,
                           allUsers.get(i).getId());
                   break;
               }
           }
        } */
        List<User> usersFound = new ArrayList<>();
        usersFound = userViewModel.findUser(email);
        if (usersFound.size() > 0) {
            verifyEmail = usersFound.get(0).getEmail().equals(email);
            verifyPassword = usersFound.get(0).getPassword().equals(password);
        }
        if (!email.equals("") && verifyEmail && verifyPassword && !password.equals("")) {
            Intent loginintent = new Intent(MainActivity.this, HomePage.class);
            SharedPrefrenceUtil.getInstance(getApplicationContext()).saveValue(IS_LOGIN, true);
            SharedPrefrenceUtil.getInstance(getApplicationContext()).saveValue(CURRENT_USER_ID,
                    usersFound.get(0).getId());
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
