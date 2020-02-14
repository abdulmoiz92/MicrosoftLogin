package com.example.microsftlogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.microsftlogin.Helpers.SharedPrefrenceHelper;
import com.example.microsftlogin.TabsFragment.Dashboard;
import com.example.microsftlogin.TabsFragment.TablayoutFragment;
import com.example.microsftlogin.Utils.SharedPrefrenceUtil;
import com.example.microsftlogin.dashboardsActivities.CalendarFragment;
import com.example.microsftlogin.dashboardsActivities.EditProfile;
import com.example.microsftlogin.dashboardsActivities.TodolistFragment;
import com.google.android.material.navigation.NavigationView;

import static com.example.microsftlogin.Utils.SharedPrefrenceUtil.USER_EMAIL;
import static com.example.microsftlogin.Utils.SharedPrefrenceUtil.USER_NAME;

public class HomePage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    SharedPrefrenceHelper sph = new SharedPrefrenceHelper();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_home_page);
        sph.mprefrences = getSharedPreferences(sph.getSharedfile(),MODE_PRIVATE);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Dashboard dashboard = new Dashboard();
        fragmentTransaction.replace(R.id.main_content, dashboard);
        fragmentTransaction.commit();
       //account_name.setText(sph.getSpString(sph.getPerson_name()));

        //Set Drawer Info From SPH

        //drawer_account_email.setText(sph.getSpString(sph.getEmail_Key()));




        /* Tab Layout */



        /* End Tab Layout */


        /* Navigation Drawer Layout */
        AddDrawer();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.addToBackStack(null);

        if (drawer != null) {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        View hView =  navigationView.getHeaderView(0);
        TextView drawer_account_name = hView.findViewById(R.id.drawer_account_name);
        drawer_account_name.setText(SharedPrefrenceUtil.getInstance(getApplicationContext()).getStringValue(USER_NAME));
        TextView drawer_account_email = hView.findViewById(R.id.drawer_account_email);
        drawer_account_email.setText(SharedPrefrenceUtil.getInstance(getApplicationContext()).getStringValue(USER_EMAIL));

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Handle navigation view item clicks here.
        switch (item.getItemId()) {
            case R.id.nav_dashboard:
                // Handle the camera import action (for now display a toast).
                Dashboard navDashboard = new Dashboard();
                fragmentTransaction.replace(R.id.main_content, navDashboard);
                fragmentTransaction.commit();
                getSupportActionBar().setTitle("Dashboard");
                drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.nav_todolist:
                // Handle the gallery action (for now display a toast).
                TodolistFragment todofragment = new TodolistFragment();
                fragmentTransaction.addToBackStack(null).replace(R.id.main_content, todofragment);
                fragmentTransaction.commit();
                getSupportActionBar().setTitle("TodoList");
                drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.nav_reports:
                // Handle the slideshow action (for now display a toast).
                drawer.closeDrawer(GravityCompat.START);
                displayToast("Reports");
                return true;
            case R.id.nav_calendar:
                // Handle the tools action (for now display a toast).
                CalendarFragment calendarfragment = new CalendarFragment();
                fragmentTransaction.addToBackStack(null).replace(R.id.main_content, calendarfragment);
                fragmentTransaction.commit();
                getSupportActionBar().setTitle("Calendar");
                drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.nav_user:
                // Handle the share action (for now display a toast).
                EditProfile profilefragment = new EditProfile();
                fragmentTransaction.addToBackStack(null).replace(R.id.main_content, profilefragment);
                fragmentTransaction.commit();
                getSupportActionBar().setTitle("User Info");
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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void logout_user(View view) {
        sph.setIslogin(false);
        sph.editSpBoolean(SharedPrefrenceHelper.getLogin_key(),sph.getisLogin());
        Intent logoutintent = new Intent(HomePage.this,MainActivity.class);
        startActivity(logoutintent);
    }


    public void logout_user_nav() {
        SharedPrefrenceUtil.getInstance(getApplicationContext()).clearKey(SharedPrefrenceUtil.IS_LOGIN);
        Intent logoutintent = new Intent(HomePage.this,MainActivity.class);
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
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (drawer != null) {
            drawer.addDrawerListener(toggle);
        }
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        View hView =  navigationView.getHeaderView(0);
        TextView drawer_account_name = hView.findViewById(R.id.drawer_account_name);
        drawer_account_name.setText(SharedPrefrenceUtil.getInstance(getApplicationContext()).getStringValue(USER_NAME));
        TextView drawer_account_email = hView.findViewById(R.id.drawer_account_email);
        drawer_account_email.setText(SharedPrefrenceUtil.getInstance(getApplicationContext()).getStringValue(USER_EMAIL));
        navigationView.setNavigationItemSelectedListener(this);
    }


}
