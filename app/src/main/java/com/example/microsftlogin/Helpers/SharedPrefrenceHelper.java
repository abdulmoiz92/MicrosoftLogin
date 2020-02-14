package com.example.microsftlogin.Helpers;

import android.content.SharedPreferences;
import android.util.Log;

import com.example.microsftlogin.Adapter.Todo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SharedPrefrenceHelper {

    public SharedPreferences mprefrences;
    public SharedPreferences tprefrences;
    private String Loginsharedfile;
    private String Todosharedfile;
    private static String login_key = "PrefrenceIsLogin";
    public static String todo_key = "TodoList";
    public static String isSecond_key = "IsSecond";
    private static String Email_Key;
    private static String Password_Key;
    private static String Person_name;
    private boolean isSecond;
    private boolean islogin;

    public SharedPrefrenceHelper() {
      Loginsharedfile  = "com.example.microsftlogin.Loginshareprefrences";
      Todosharedfile  = "com.example.microsftlogin.Todoshareprefrences";
      Email_Key = "EmailAddress";
      Password_Key = "Password";
      Person_name = "Person_Name";
      islogin = false;
      isSecond = false;
    }

    public String getSharedfile() {
        return Loginsharedfile;
    }

    public String getTodoSharedfile() {
        return Todosharedfile;
    }

    public static String getTodo_Key() {
        return todo_key;
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

    public static String getIsSecond_key() { return isSecond_key; }

    public boolean getIsSecond() {
        return isSecond;
    }

    public void setIsSecond(boolean isSecondTime) {
        this.isSecond = isSecondTime;
    }

    // Shared Preference Editor & Getter
    public void editSpString(String key, String text) {
        SharedPreferences.Editor prefrenceEditor = mprefrences.edit();
        prefrenceEditor.putString(key,text);
        prefrenceEditor.apply();

    }

    public void editSpInt(String key, Integer integer) {
        SharedPreferences.Editor prefrenceEditor = mprefrences.edit();
        prefrenceEditor.putInt(key,integer);
        prefrenceEditor.apply();

    }

        /*  CODE MADE SHORT
        SharedPreferences.Editor prefrenceEditor = sph.mprefrences.edit();
            prefrenceEditor.putString(sph.getPerson_name(),name);
            prefrenceEditor.putString(sph.getEmail_Key(), email);
            prefrenceEditor.putString(sph.getPassword_Key(), password);
            prefrenceEditor.apply(); */


    public String getSpString(String key) {
      return mprefrences.getString(key,"");
    }

    public Integer getSpInt(String key) {
        return mprefrences.getInt(key, 0);
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

    public List<Todo> getSpArray(String key, List<Todo> todosArrayList) {
        Gson gson = new Gson();
        String json = tprefrences.getString(key, "");
        /*Type type = new TypeToken<ArrayList<Todo>>() {}.getType();*/
        todosArrayList = gson.fromJson(json, new TypeToken<List<Todo>>(){}.getType());
        return todosArrayList;
    }

    public void editSpArray(String key, List<Todo> todoArrayList) {
        SharedPreferences.Editor prefrenceEditor = tprefrences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(todoArrayList);
        prefrenceEditor.putString(key, json);
        prefrenceEditor.apply();
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
