package com.example.microsftlogin.AboutUserDatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "about_user_table")
public class AboutUser {

    @NonNull
    @PrimaryKey
    private String id;

    @NonNull
    @ColumnInfo(name = "aboutuser_name")
    private String name;

    @NonNull
    @ColumnInfo(name = "aboutuser_email")
    private String email;

    @NonNull
    @ColumnInfo(name = "aboutuser_phone")
    private String phone;

    @Nullable
    @ColumnInfo(name = "aboutuser_address")
    private String address;

    @Nullable
    @ColumnInfo(name = "aboutuser_education")
    private String educationDegree;

    @Nullable
    @ColumnInfo(name = "aboutuser_description")
    private String description;

    @NonNull
    @ColumnInfo(name = "aboutuser_id")
    private String userId;


    public AboutUser(String id, String name, String email, String phone, String address, String educationDegree,
                     String description, String userId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.educationDegree = educationDegree;
        this.description = description;
        this.userId = userId;
    }

    @Ignore
    public AboutUser() {
        //For Datasnapshots
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    @NonNull
    public String getPhone() {
        return phone;
    }

    public void setPhone(@NonNull String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEducationDegree() {
        return educationDegree;
    }

    public void setEducationDegree(String educationDegree) {
        this.educationDegree = educationDegree;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
