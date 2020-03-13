package com.example.microsftlogin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.microsftlogin.Helpers.SharedPrefrenceHelper;
import com.example.microsftlogin.StartUpActivities.MainActivity;
import com.example.microsftlogin.Utils.SharedPrefrenceUtil;
import com.google.android.material.navigation.NavigationView;

import static com.example.microsftlogin.Utils.SharedPrefrenceUtil.USER_EMAIL;
import static com.example.microsftlogin.Utils.SharedPrefrenceUtil.USER_NAME;

public class HomePage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    SharedPrefrenceHelper sph = new SharedPrefrenceHelper();

    NavController navController;
    AppBarConfiguration appBarConfiguration;
    Toolbar toolbar;
    NavigationView navigationView;
    DrawerLayout drawer;
    View hView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_home_page);
        sph.mprefrences = getSharedPreferences(sph.getSharedfile(),MODE_PRIVATE);


        //Set Drawer Info From SPH
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navController = Navigation.findNavController(this, R.id.fragment);
        appBarConfiguration =
                new AppBarConfiguration.Builder(navController.getGraph())
                        .setDrawerLayout(drawer)
                        .build();
        toolbar = findViewById(R.id.toolbar);
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);
        hView =  navigationView.getHeaderView(0);

        //drawer_account_email.setText(sph.getSpString(sph.getEmail_Key()));




        /* Tab Layout */



        /* End Tab Layout */


        /* Navigation Drawer Layout */
        AddDrawer();

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        TextView drawer_account_name = hView.findViewById(R.id.drawer_account_name);
        drawer_account_name.setText(SharedPrefrenceUtil.getInstance(getApplicationContext()).getStringValue(USER_NAME));
        TextView drawer_account_email = hView.findViewById(R.id.drawer_account_email);
        drawer_account_email.setText(SharedPrefrenceUtil.getInstance(getApplicationContext()).getStringValue(USER_EMAIL));


        // Handle navigation view item clicks here.
        switch (item.getItemId()) {
            case R.id.nav_dashboard:
                // Handle the camera import action (for now display a toast).
                navController.navigate(R.id.navfragmentdashboard);
               // getSupportActionBar().setTitle("Dashboard");
                drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.nav_todolist:
                // Handle the gallery action (for now display a toast).
                navController.navigate(R.id.todolistFragment);
               // getSupportActionBar().setTitle("TodoList");
                drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.nav_reports:
                // Handle the slideshow action (for now display a toast).
                drawer.closeDrawer(GravityCompat.START);
                displayToast("Reports");
                return true;
            case R.id.nav_calendar:
                // Handle the tools action (for now display a toast).
                navController.navigate(R.id.calendarFragment);
               // getSupportActionBar().setTitle("Calendar");
                drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.nav_user:
                // Handle the share action (for now display a toast).
                navController.navigate(R.id.editProfileFragment);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.nav_setting:
                // Handle the send action (for now display a toast).
                drawer.closeDrawer(GravityCompat.START);
                displayToast("Setting");
                return true;
            case R.id.nav_logout:
                logout_user_nav();
                drawer.closeDrawer(GravityCompat.START);
             return true;

            default:
                return false;
        }
    }

    /*@Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp();
    }*/

    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }


    public void logout_user_nav() {
        SharedPrefrenceUtil.getInstance(getApplicationContext()).clearKey(SharedPrefrenceUtil.IS_LOGIN);
        Intent logoutintent = new Intent(HomePage.this, MainActivity.class);
        startActivity(logoutintent);
    }

    public void delete_user(View view) {
        sph.setIslogin(false);
        sph.deleteSpDetails();
        Intent logoutintent = new Intent(HomePage.this,MainActivity.class);
        startActivity(logoutintent);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void AddDrawer() {


        TextView drawer_account_name = hView.findViewById(R.id.drawer_account_name);
        drawer_account_name.setText(SharedPrefrenceUtil.getInstance(getApplicationContext()).getStringValue(USER_NAME));
        TextView drawer_account_email = hView.findViewById(R.id.drawer_account_email);
        drawer_account_email.setText(SharedPrefrenceUtil.getInstance(getApplicationContext()).getStringValue(USER_EMAIL));
        navigationView.setNavigationItemSelectedListener(this);
    }


}
