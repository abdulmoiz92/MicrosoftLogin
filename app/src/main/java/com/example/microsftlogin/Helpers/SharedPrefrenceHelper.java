package com.example.microsftlogin.Helpers;

import android.content.SharedPreferences;

public class SharedPrefrenceHelper {

    public SharedPreferences mprefrences;
    private String sharedfile;
    private static String login_key = "PrefrenceIsLogin";
    private static String Email_Key;
    private static String Password_Key;
    private static String Person_name;
    private boolean islogin;

    public SharedPrefrenceHelper() {
      sharedfile  = "com.example.microsftlogin.Loginshareprefrences";
      Email_Key = "EmailAddress";
      Password_Key = "Password";
      Person_name = "Person_Name";
      islogin = false;
    }

    public String getSharedfile() {
        return sharedfile;
    }

    public static String getEmail_Key() {
        return Email_Key;
    }

    public static String getPassword_Key() {
        return Password_Key;
    }

    public static String getPerson_name() {
        return Person_name;
    }

    public static String getLogin_key() {
        return login_key;
    }

    public void setIslogin(boolean islogin) {
        this.islogin = islogin;
    }

    public Boolean getisLogin() {
        return islogin;
    }

    // Shared Preference Editor & Getter
    public void editSpString(String key, String text) {
        SharedPreferences.Editor prefrenceEditor = mprefrences.edit();
        prefrenceEditor.putString(key,text);
        prefrenceEditor.apply();

        /*  CODE MADE SHORT
        SharedPreferences.Editor prefrenceEditor = sph.mprefrences.edit();
            prefrenceEditor.putString(sph.getPerson_name(),name);
            prefrenceEditor.putString(sph.getEmail_Key(), email);
            prefrenceEditor.putString(sph.getPassword_Key(), password);
            prefrenceEditor.apply(); */

    }

    public String getSpString(String key) {
      return mprefrences.getString(key,"");
    }

    public void editSpBoolean(String key, boolean value) {
        SharedPreferences.Editor prefrenceEditor = mprefrences.edit();
        prefrenceEditor.putBoolean(key,value);
        prefrenceEditor.apply();

        /* CODE MADE SHORT
        SharedPreferences.Editor prefrenceEditor = sph.mprefrences.edit();
            prefrenceEditor.putBoolean(sph.getLogin_key(),sph.getisLogin());
            prefrenceEditor.apply(); */
    }

    public boolean getSpBoolean(String key, boolean defaultValue) {
        return mprefrences.getBoolean(key,defaultValue);
    }

    public void deleteSpDetails() {
        SharedPreferences.Editor prefrenceEditor = mprefrences.edit();
        prefrenceEditor.clear();
        prefrenceEditor.apply();
    }
}
