package com.example.microsftlogin.StartUpActivities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.microsftlogin.AboutUserDatabase.AboutUser;
import com.example.microsftlogin.AboutUserDatabase.AboutUserViewModel;
import com.example.microsftlogin.Helpers.SharedPrefrenceHelper;
import com.example.microsftlogin.HomePage;
import com.example.microsftlogin.R;
import com.example.microsftlogin.UserAchievementsDatabase.UserAchievement;
import com.example.microsftlogin.UserAchievementsDatabase.UserAchievementViewModel;
import com.example.microsftlogin.UserDatabase.User;
import com.example.microsftlogin.UserDatabase.UserViewModel;
import com.example.microsftlogin.UserEducationDatabase.UserEducation;
import com.example.microsftlogin.UserEducationDatabase.UserEducationViewModel;
import com.example.microsftlogin.UserExperienceDatabase.UserExperience;
import com.example.microsftlogin.UserExperienceDatabase.UserExperienceViewModel;
import com.example.microsftlogin.UserProjectsDatabase.UserProject;
import com.example.microsftlogin.UserProjectsDatabase.UserProjectViewModel;
import com.example.microsftlogin.UserSkillsDatabase.UserSkill;
import com.example.microsftlogin.UserSkillsDatabase.UserSkillViewModel;
import com.example.microsftlogin.Utils.SharedPrefrenceUtil;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

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
    private ConstraintLayout loginMain;
    private Button loginbtn;
    private ProgressBar progressBar;
    private UserViewModel userViewModel;
    private AboutUserViewModel aboutUserViewModel;
    private List<User> allUsers = new ArrayList<>();

    boolean connected = false;

    //Firebase
    FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupFirebaseAuth();
        setContentView(R.layout.activity_main);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        aboutUserViewModel = ViewModelProviders.of(this).get(AboutUserViewModel.class);

        loginbtn = findViewById(R.id.login);

        if (!SharedPrefrenceUtil.getInstance(getApplicationContext()).getBooleanValue(IS_FIRST_TIME_LAUNCH)) {
            Intent splashScreen = new Intent(this, SplashScreen.class);
            startActivity(splashScreen);
            finish();
        }

/*        else if (SharedPrefrenceUtil.getInstance(getApplicationContext()).getBooleanValue(IS_LOGIN)) {
            Intent homePage = new Intent(MainActivity.this, HomePage.class);
            //homePage.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homePage);
            finish();
        }*/

        else {

            login_email = findViewById(R.id.email);
            login_password = findViewById(R.id.password);
            login_error_message = findViewById(R.id.login_error);
            loginMain = findViewById(R.id.login_main);
            progressBar = findViewById(R.id.progressbar);

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
        finish();
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

    public void check_login_details(final View view) {
        String email = login_email.getEditText().getText().toString();
        String password = login_password.getEditText().getText().toString();
        boolean verifyEmail = false;
        boolean verifyPassword = false;
        loginbtn.setVisibility(View.GONE);
        progressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.main_background),
                android.graphics.PorterDuff.Mode.SRC_ATOP);
        progressBar.setVisibility(View.VISIBLE);
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
       /* List<User> usersFound = new ArrayList<>();
        usersFound = userViewModel.findUser(email);
        if (usersFound.size() > 0) {
            verifyEmail = usersFound.get(0).getEmail().equals(email);
            verifyPassword = usersFound.get(0).getPassword().equals(password);
        } */
        if (!email.equals("") && !password.equals("")) {
            /*Intent loginintent = new Intent(MainActivity.this, HomePage.class);
            SharedPrefrenceUtil.getInstance(getApplicationContext()).saveValue(IS_LOGIN, true);
            SharedPrefrenceUtil.getInstance(getApplicationContext()).saveValue(CURRENT_USER_ID,
                    usersFound.get(0).getId());
            loginintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(loginintent);*/
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
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        final DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("userinfo");
                        addData(userRef, 1);

                        DatabaseReference aboutRef = FirebaseDatabase.getInstance().getReference().child("users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("about");
                        addData(aboutRef, 2);

                        DatabaseReference expRef = FirebaseDatabase.getInstance().getReference().child("users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("experiences");
                        addData(expRef, 3);

                        DatabaseReference eduRef = FirebaseDatabase.getInstance().getReference().child("users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("educations");
                        addData(eduRef, 4);

                        DatabaseReference skillRef = FirebaseDatabase.getInstance().getReference().child("users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("skills");
                        addData(skillRef, 5);

                        DatabaseReference projRef = FirebaseDatabase.getInstance().getReference().child("users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("projects");
                        addData(projRef, 6);

                        DatabaseReference achRef = FirebaseDatabase.getInstance().getReference().child("users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("achievements");
                        addData(achRef, 7);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        loginbtn.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(MainActivity.this, "This Email Is Not Registered", Toast.LENGTH_SHORT).show();
                    }
                });

            } else {
                loginbtn.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this,"Please Check Your Internet Connection",Toast.LENGTH_SHORT).show();
            }

        } else {
            login_error_message.setVisibility(View.VISIBLE);
            loginbtn.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }

    private void setupFirebaseAuth() {
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    loginMain.setVisibility(View.INVISIBLE);
                    Handler handler = new Handler();
                    final int[] timeToDelay = {3000};
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                         String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                         if (userViewModel.findUserWithAbout(user_id) != null) {
                             Intent intent = new Intent(MainActivity.this, HomePage.class);
                             startActivity(intent);
                             finish();
                         } else {
                             timeToDelay[0] = timeToDelay[0] + 2000;
                         }
                        }
                    }, timeToDelay[0]);
                }

                }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
            FirebaseAuth.getInstance().addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthStateListener != null) {
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthStateListener);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("IsUserLogin", sph.getisLogin());
    }

    public void addData(final Query query, final int number) {
      query.addListenerForSingleValueEvent(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              for (DataSnapshot singleSnashot: dataSnapshot.getChildren()) {
                  if (number == 1) {
                      User user = singleSnashot.getValue(User.class);
                      UserViewModel userViewModel = ViewModelProviders.of(MainActivity.this).get(UserViewModel.class);
                      userViewModel.insert(user);
                  } else if (number == 2) {
                      AboutUser aboutUser = singleSnashot.getValue(AboutUser.class);
                      AboutUserViewModel aboutUserViewModel = ViewModelProviders.of(MainActivity.this).get(AboutUserViewModel.class);
                      aboutUserViewModel.insert(aboutUser);
                  } else if(number == 3) {
                      UserExperience userExperience = singleSnashot.getValue(UserExperience.class);
                      UserExperienceViewModel userExperienceViewModel = ViewModelProviders.of(MainActivity.this).get(UserExperienceViewModel.class);
                      userExperienceViewModel.insert(userExperience);
                  } else if(number == 4) {
                      UserEducation userEducation = singleSnashot.getValue(UserEducation.class);
                      UserEducationViewModel userEducationViewModel = ViewModelProviders.of(MainActivity.this).get(UserEducationViewModel.class);
                      userEducationViewModel.insert(userEducation);
                  } else if(number == 5) {
                      UserSkill userSkill = singleSnashot.getValue(UserSkill.class);
                      UserSkillViewModel userSkillViewModel = ViewModelProviders.of(MainActivity.this).get(UserSkillViewModel.class);
                      userSkillViewModel.insert(userSkill);
                  } else if(number == 6) {
                      UserProject userProject = singleSnashot.getValue(UserProject.class);
                      UserProjectViewModel userProjectViewModel = ViewModelProviders.of(MainActivity.this).get(UserProjectViewModel.class);
                      userProjectViewModel.insert(userProject);
                  } else if(number == 7) {
                      UserAchievement userAchievement = singleSnashot.getValue(UserAchievement.class);
                      UserAchievementViewModel userAchievementViewModel = ViewModelProviders.of(MainActivity.this).get(UserAchievementViewModel.class);
                      userAchievementViewModel.insert(userAchievement);
                  }
              }
          }

          @Override
          public void onCancelled(@NonNull DatabaseError databaseError) {

          }
      });
    }
}
