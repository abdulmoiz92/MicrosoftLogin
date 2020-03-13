package com.example.microsftlogin.UserExperienceDatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_experience_table")
public class UserExperience {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "job_title")
    private String mJobTitle;

    @NonNull
    @ColumnInfo(name = "company_name")
    private String mCompanyName;

    @NonNull
    @ColumnInfo(name = "worked_from")
    private String mWorkedFrom;

    @NonNull
    @ColumnInfo(name = "worked_till")
    private String mWorkedTill;

    @Nullable
    @ColumnInfo(name = "city_or_country")
    private String mCityOrCountry;

    @Nullable
    @ColumnInfo(name = "tasks_performed")
    private String mTasksPerformed;

    @NonNull
    @ColumnInfo(name = "experienceuser_id")
    private int userId;

    public UserExperience(String mJobTitle, String mCompanyName, String mWorkedFrom, String mWorkedTill,
                          String mCityOrCountry, String mTasksPerformed, int userId) {
        this.mJobTitle = mJobTitle;
        this.mCompanyName = mCompanyName;
        this.mWorkedFrom = mWorkedFrom;
        this.mWorkedTill = mWorkedTill;
        this.mCityOrCountry = mCityOrCountry;
        this.mTasksPerformed = mTasksPerformed;
        this.userId = userId;
    }

    @Ignore
    public UserExperience(int id, String mJobTitle, String mCompanyName, String mWorkedFrom, String mWorkedTill,
                          String mCityOrCountry, String mTasksPerformed, int userId) {
        this.id = id;
        this.mJobTitle = mJobTitle;
        this.mCompanyName = mCompanyName;
        this.mWorkedFrom = mWorkedFrom;
        this.mWorkedTill = mWorkedTill;
        this.mCityOrCountry = mCityOrCountry;
        this.mTasksPerformed = mTasksPerformed;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getJobTitle() {
        return mJobTitle;
    }

    public void setJobTitle(@NonNull String mJobTitle) {
        this.mJobTitle = mJobTitle;
    }

    @NonNull
    public String getCompanyName() {
        return mCompanyName;
    }

    public void setCompanyName(@NonNull String mCompanyName) {
        this.mCompanyName = mCompanyName;
    }

    @NonNull
    public String getWorkedFrom() {
        return mWorkedFrom;
    }

    public void setWorkedFrom(@NonNull String mWorkedFrom) {
        this.mWorkedFrom = mWorkedFrom;
    }

    @NonNull
    public String getWorkedTill() {
        return mWorkedTill;
    }

    public void setWorkedTill(@NonNull String mWorkedTill) {
        this.mWorkedTill = mWorkedTill;
    }

    @Nullable
    public String getCityOrCountry() {
        return mCityOrCountry;
    }

    public void setCityOrCountry(@Nullable String mCityOrCountry) {
        this.mCityOrCountry = mCityOrCountry;
    }

    @Nullable
    public String getTasksPerformed() {
        return mTasksPerformed;
    }

    public void setTasksPerformed(@Nullable String mTasksPerformed) {
        this.mTasksPerformed = mTasksPerformed;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
