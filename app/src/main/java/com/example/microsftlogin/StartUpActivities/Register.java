package com.example.microsftlogin.StartUpActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.microsftlogin.AboutUserDatabase.AboutUser;
import com.example.microsftlogin.Helpers.SharedPrefrenceHelper;
import com.example.microsftlogin.HomePage;
import com.example.microsftlogin.R;
import com.example.microsftlogin.UserDatabase.User;
import com.example.microsftlogin.UserDatabase.UserViewModel;
import com.example.microsftlogin.Utils.SharedPrefrenceUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.microsftlogin.Utils.SharedPrefrenceUtil.IS_FIRST_TIME_LAUNCH;
import static com.example.microsftlogin.Utils.SharedPrefrenceUtil.IS_LOGIN;

public class Register extends AppCompatActivity {

    SharedPrefrenceHelper sph = new SharedPrefrenceHelper();
    private TextInputLayout register_name;
    private TextInputLayout register_email;
    private TextInputLayout register_password;
    private TextInputLayout register_confirm_password;
    private TextView error_message;
    private UserViewModel userViewModel;
    List<User> allUsers = new ArrayList<>();
    List<User> findUsers = new ArrayList<>();
    Boolean connected = false;
    Button register_btn;
    ProgressBar register_progress;

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
        register_btn = findViewById(R.id.register_btn);
        register_progress = findViewById(R.id.register_progress);

        if (!SharedPrefrenceUtil.getInstance(getApplicationContext()).getBooleanValue(IS_FIRST_TIME_LAUNCH)) {
            Intent splashScreen = new Intent(this, SplashScreen.class);
            startActivity(splashScreen);
        }

        else if (SharedPrefrenceUtil.getInstance(getApplicationContext()).getBooleanValue(IS_LOGIN)) {
            Intent homePage = new Intent(Register.this, HomePage.class);
            //homePage.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homePage);
        }

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.getuAllUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                allUsers = users;
            }
        });

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
        final String name = register_name.getEditText().getText().toString();
        final String email = register_email.getEditText().getText().toString();
        final String password = register_password.getEditText().getText().toString();
        String confirm_password = register_confirm_password.getEditText().getText().toString();
        Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Matcher mat = pattern.matcher(email);
        register_btn.setVisibility(View.GONE);
        register_progress.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.main_background),
                android.graphics.PorterDuff.Mode.SRC_ATOP);
        register_progress.setVisibility(View.VISIBLE);

       /* if (allUsers.size() < 1) { */

            if (!name.isEmpty() && mat.matches() && !password.isEmpty() && password.equals(confirm_password)) {
               /* Intent register_intent = new Intent(Register.this, MainActivity.class);
                register_intent.putExtra("email", email);
                register_intent.putExtra("password", password);
                //register_intent.putExtra(Email_Key,mprefrences.getString(Email_Key,""));
                //register_intent.putExtra(Password_Key,mprefrences.getString(Password_Key,""));
                User newUser = new User(name, email, password);
                userViewModel.insert(newUser);
                Toast.makeText(this, "Registered Successfully", Toast.LENGTH_LONG).show();
                startActivity(register_intent); */
                // setResult(RESULT_OK, register_intent);
                ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                    //we are connected to a network
                    connected = true;
                }
                else {
                    connected = false;
                }
                if (connected == true) {
                    registerNewEmail(name, email, password);
                } else {
                    register_btn.setVisibility(View.VISIBLE);
                    register_progress.setVisibility(View.GONE);
                    Toast.makeText(this, "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                }


            } else {
                error_message.setVisibility(view.VISIBLE);
            }

        } /* else {

            boolean verifyEmail = false;
            List<User> usersFound = new ArrayList<>();
            usersFound = userViewModel.findUser(email);
            if (usersFound.size() > 0) {
                verifyEmail = usersFound.get(0).getEmail().equals(email);
            }

                if (!name.isEmpty() && mat.matches() && !verifyEmail && !password.isEmpty() && password.equals(confirm_password)) {
                    Intent register_intent = new Intent(Register.this, MainActivity.class);
                    register_intent.putExtra("email", email);
                    register_intent.putExtra("password", password);
                    //register_intent.putExtra(Email_Key,mprefrences.getString(Email_Key,""));
                    //register_intent.putExtra(Password_Key,mprefrences.getString(Password_Key,""));
                    User newUser = new User(name, email, password);
                    userViewModel.insert(newUser);
                    startActivity(register_intent);
                    // setResult(RESULT_OK, register_intent);
                }

             else if (verifyEmail) {
                Toast.makeText(this, "Email Already Registered", Toast.LENGTH_LONG).show();
            } else {
                error_message.setVisibility(view.VISIBLE);
            }

        } */
    //}

    public void already_have_account(View view) {
         Intent loginIntent = new Intent(Register.this,MainActivity.class);
         startActivity(loginIntent);
         finish();
    }

    private void registerNewEmail(final String name, final String email, final String password) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                    final FirebaseUser newUser = FirebaseAuth.getInstance().getCurrentUser();
                    final User user = new User(newUser.getUid(),name,email,password);

                    FirebaseDatabase.getInstance().getReference()
                            .child("users").child(newUser.getUid()).child("userinfo").push()
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                DatabaseReference aboutUserRef = FirebaseDatabase.getInstance().getReference().child("users")
                                        .child(newUser.getUid()).child("about").push();

                                AboutUser aboutUser = new AboutUser(aboutUserRef.getKey(),user.getName(),user.getEmail(),"","","","",
                                        user.getId());
                                FirebaseAuth.getInstance().signOut();
                                aboutUserRef.setValue(aboutUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Intent register_intent = new Intent(Register.this, MainActivity.class);
                                        register_intent.putExtra("email", email);
                                        register_intent.putExtra("password", password);
                                        startActivity(register_intent);
                                        finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        FirebaseAuth.getInstance().signOut();
                                        Toast.makeText(Register.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            FirebaseAuth.getInstance().signOut();
                            Toast.makeText(Register.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                        }
                    });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Register.this, "Email Already Registered", Toast.LENGTH_SHORT).show();
            }
        });
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