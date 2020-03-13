package com.example.microsftlogin.AboutUserDatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "about_user_table")
public class AboutUser {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "aboutuser_name")
    private String mName;

    @NonNull
    @ColumnInfo(name = "aboutuser_email")
    private String mEmail;

    @NonNull
    @ColumnInfo(name = "aboutuser_phone")
    private String mPhone;

    @Nullable
    @ColumnInfo(name = "aboutuser_address")
    private String mAddress;

    @Nullable
    @ColumnInfo(name = "aboutuser_education")
    private String mEducationDegree;

    @Nullable
    @ColumnInfo(name = "aboutuser_description")
    private String mDescription;

    @Nullable
    @ColumnInfo(name = "aboutuser_id")
    private int userId;

    public AboutUser(String mName, String mEmail, String mPhone, String mAddress, String mEducationDegree,
                     String mDescription, int userId) {
        this.mName = mName;
        this.mEmail = mEmail;
        this.mPhone = mPhone;
        this.mAddress = mAddress;
        this.mEducationDegree = mEducationDegree;
        this.mDescription = mDescription;
        this.userId = userId;
    }

    @Ignore
    public AboutUser(int id,String mName, String mEmail, String mPhone, String mAddress, String mEducationDegree,
                     String mDescription, int userId) {
        this.id = id;
        this.mName = mName;
        this.mEmail = mEmail;
        this.mPhone = mPhone;
        this.mAddress = mAddress;
        this.mEducationDegree = mEducationDegree;
        this.mDescription = mDescription;
        this.userId = userId;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return mName;
    }

    public void setName(@NonNull String mName) {
        this.mName = mName;
    }

    @NonNull
    public String getEmail() {
        return mEmail;
    }

    public void setEmail(@NonNull String mEmail) {
        this.mEmail = mEmail;
    }

    @NonNull
    public String getPhone() {
        return mPhone;
    }

    public void setPhone(@NonNull String mPhone) {
        this.mPhone = mPhone;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public String getEducationDegree() {
        return mEducationDegree;
    }

    public void setEducationDegree(String mEducationDegree) {
        this.mEducationDegree = mEducationDegree;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
