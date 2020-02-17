package com.example.microsftlogin.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefrenceUtil {
    public static final String PREF_NAME = "business_prefs";//"androidhive-welcome";
    public static final String IS_FIRST_TIME_LAUNCH = "is_first_launch";
    public static final String IS_LOGIN= "is_login";
    public static final String USER_ID = "user_id";
    public static final String USER_NAME = "user_name";
    public static final String USER_EMAIL = "user_email";
    public static final String USER_PASSWORD = "user_password";
    public static final String COMPANY = "company";
    public static final String BALANCE = "balance";
    public static final String FIREBASE_TOKEN = "firebase_token";
    public static final String FCM_TOKEN = "fcm_token";
    public static final String MERCHANT_ID = "merchant_id";
    public static final String STORE_UNIQUE_ID = "store_unique_id";

    public static final String SESSION_USERNAME = "session_username";
    public static final String SESSION_USERID = "session_userid";


    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private static SharedPrefrenceUtil p;

    public static SharedPrefrenceUtil getInstance(Context context){
        if(p ==null)
            p = new SharedPrefrenceUtil(context);

        return p;
    }

    private SharedPrefrenceUtil(Context context){
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }


    public void saveValue(String key, String val){
        editor = prefs.edit();
        editor.putString(key, val);
        editor.apply();
    }

    public void saveValue(String key, int val){
        editor = prefs.edit();
        editor.putInt(key, val);
        editor.apply();
    }

    public void saveValue(String key, boolean val){
        editor = prefs.edit();
        editor.putBoolean(key, val);
        editor.apply();
    }

    public String getStringValue(String key){
        return prefs.getString(key, null);
    }

    public int getIntValue(String key){
        return prefs.getInt(key, -1);
    }

    public boolean getBooleanValue(String key){
        return prefs.getBoolean(key, false);
    }

    public void clearKey(String key){
        editor = prefs.edit();
        editor.remove(key);
        editor.apply();
    }

}
