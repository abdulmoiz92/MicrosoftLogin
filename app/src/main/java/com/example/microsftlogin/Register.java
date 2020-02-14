package com.example.microsftlogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.microsftlogin.Helpers.SharedPrefrenceHelper;
import com.example.microsftlogin.Utils.SharedPrefrenceUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.microsftlogin.Utils.SharedPrefrenceUtil.USER_EMAIL;
import static com.example.microsftlogin.Utils.SharedPrefrenceUtil.USER_NAME;
import static com.example.microsftlogin.Utils.SharedPrefrenceUtil.USER_PASSWORD;

public class Register extends AppCompatActivity {

    SharedPrefrenceHelper sph = new SharedPrefrenceHelper();
    private EditText register_name;
    private EditText register_email;
    private EditText register_password;
    private EditText register_confirm_password;
    private TextView error_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        sph.mprefrences = getSharedPreferences(sph.getSharedfile(),MODE_PRIVATE);
        register_name = findViewById(R.id.register_name);
        register_email = findViewById(R.id.register_email);
        register_password = findViewById(R.id.register_password);
        register_confirm_password = findViewById(R.id.register_confirm_password);
        error_message = findViewById(R.id.error_message);

        if (savedInstanceState != null) {
            boolean error_visible = savedInstanceState.getBoolean("error_message_visibility");
             if (error_visible) {
                 error_message.setVisibility(View.VISIBLE);
                 error_message.setText(savedInstanceState.getString("error_message"));
             }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void direct_to_login(View view) {
        String name = register_name.getText().toString();
        String email = register_email.getText().toString();
        String password = register_password.getText().toString();
        String confirm_password = register_confirm_password.getText().toString();
        Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Matcher mat = pattern.matcher(email);
        if (!name.isEmpty() && mat.matches() && !password.isEmpty() && password.equals(confirm_password) )  {
            Intent register_intent = new Intent(Register.this,MainActivity.class);
            register_intent.putExtra("email", email);
            //register_intent.putExtra(Email_Key,mprefrences.getString(Email_Key,""));
            //register_intent.putExtra(Password_Key,mprefrences.getString(Password_Key,""));
            startActivity(register_intent);
           // setResult(RESULT_OK, register_intent);


            SharedPrefrenceUtil.getInstance(getApplicationContext()).saveValue(USER_NAME, name);
            SharedPrefrenceUtil.getInstance(getApplicationContext()).saveValue(USER_EMAIL, email);
            SharedPrefrenceUtil.getInstance(getApplicationContext()).saveValue(USER_PASSWORD, password);

            finish();
        } else {
            error_message.setVisibility(view.VISIBLE);
        }
    }

    public void already_have_account(View view) {
         finish();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
         if (error_message.getVisibility() == View.VISIBLE) {
             outState.putBoolean("error_message_visibility",true);
             outState.putString("error_message",error_message.getText().toString());
         }
    }
}
