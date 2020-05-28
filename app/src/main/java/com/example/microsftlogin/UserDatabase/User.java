package com.example.microsftlogin.UserDatabase;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_table")
public class User {

    @NonNull
    @PrimaryKey
    private String id;

    @NonNull
    @ColumnInfo(name = "user_email")
    private String email;

    @NonNull
    @ColumnInfo(name = "user_name")
    private String name;

    @NonNull
    @ColumnInfo(name = "user_password")
    private String password;

   /* public User(@NonNull String name,@NonNull String email,@NonNull String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    } */

    public User(String id,String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    @Ignore
    public User() {
        // For Datasnapshot
    }

    // Getters & Setters

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }
}
