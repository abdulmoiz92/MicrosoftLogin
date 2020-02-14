package com.example.microsftlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.microsftlogin.Helpers.SharedPrefrenceHelper;
import com.example.microsftlogin.IntroScreens.WelcomeActivity;
import com.example.microsftlogin.Utils.SharedPrefrenceUtil;

import static com.example.microsftlogin.Utils.SharedPrefrenceUtil.IS_FIRST_TIME_LAUNCH;

public class SplashScreen extends AppCompatActivity {

    Handler handler;
    SharedPrefrenceHelper sph = new SharedPrefrenceHelper();
    MainActivity main = new MainActivity();
    ImageView imageView;
    TextView welcome_text;
    TextView we_care;
    Animation anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        imageView = findViewById(R.id.logo);
        welcome_text = findViewById(R.id.welcome_text);
        we_care = findViewById(R.id.we_care);
        anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade);

      /*  handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        },3000); */
    }

    @Override
    protected void onStart() {
        super.onStart();
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent loginintent = new Intent(SplashScreen.this, WelcomeActivity.class);
                startActivity(loginintent);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        imageView.startAnimation(anim);
        welcome_text.startAnimation(anim);
        we_care.startAnimation(anim);
    }
}
