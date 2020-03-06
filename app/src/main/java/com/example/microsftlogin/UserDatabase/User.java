package com.example.microsftlogin.UserDatabase;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_table")
public class User {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "user_email")
    private String mEmail;

    @NonNull
    @ColumnInfo(name = "user_name")
    private String mName;

    @NonNull
    @ColumnInfo(name = "user_password")
    private String mPassword;

    public User(@NonNull String name,@NonNull String email,@NonNull String password) {
        this.mName = name;
        this.mEmail = email;
        this.mPassword = password;
    }

    @Ignore
    public User(int id,String name, String email, String password) {
        this.id = id;
        this.mName = name;
        this.mEmail = email;
        this.mPassword = password;
    }

    // Getters & Setters

    @NonNull
    public String getEmail() {
        return mEmail;
    }

    public void setEmail(@NonNull String email) {
        this.mEmail = email;
    }

    @NonNull
    public String getPassword() {
        return mPassword;
    }

    public void setPassword(@NonNull String password) {
        this.mPassword = password;
    }

    @NonNull
    public String getName() {
        return mName;
    }

    public void setName(@NonNull String name) {
        this.mName = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
