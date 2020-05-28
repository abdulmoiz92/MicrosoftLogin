package com.example.microsftlogin.UserEducationDatabase;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_education_table")
public class UserEducation {

    @NonNull
    @PrimaryKey
    private String id;

    @NonNull
    @ColumnInfo(name = "course_name")
    private String mCourseName;

    @NonNull
    @ColumnInfo(name = "school_or_website")
    private String mSchoolOrWebsite;

    @NonNull
    @ColumnInfo(name = "studied_from")
    private String mStudiedFrom;

    @NonNull
    @ColumnInfo(name = "studied_till")
    private String mStudiedTill;

    @NonNull
    @ColumnInfo(name = "city_or_country")
    private String mCityOrCountry;

    @NonNull
    @ColumnInfo(name = "subcourses_or_tasks")
    private String mSubcoursesOrTasks;

    @NonNull
    @ColumnInfo(name = "educationuser_id")
    private String userId;

   public UserEducation(String id,String mCourseName, String mSchoolOrWebsite, String mStudiedFrom, String mStudiedTill, String mCityOrCountry,
                  String mSubcoursesOrTasks,String userId) {
        this.id = id;
        this.mCourseName = mCourseName;
        this.mSchoolOrWebsite = mSchoolOrWebsite;
        this.mStudiedFrom = mStudiedFrom;
        this.mStudiedTill = mStudiedTill;
        this.mCityOrCountry = mCityOrCountry;
        this.mSubcoursesOrTasks = mSubcoursesOrTasks;
        this.userId = userId;
    }

    @Ignore
    public UserEducation() {
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
    public String getCourseName() {
        return mCourseName;
    }

    public void setCourseName(@NonNull String mCourseName) {
        this.mCourseName = mCourseName;
    }

    @NonNull
    public String getSchoolOrWebsite() {
        return mSchoolOrWebsite;
    }

    public void setSchoolOrWebsite(@NonNull String mSchoolOrWebsite) {
        this.mSchoolOrWebsite = mSchoolOrWebsite;
    }

    @NonNull
    public String getStudiedFrom() {
        return mStudiedFrom;
    }

    public void setStudiedFrom(@NonNull String mStudiedFrom) {
        this.mStudiedFrom = mStudiedFrom;
    }

    @NonNull
    public String getStudiedTill() {
        return mStudiedTill;
    }

    public void setStudiedTill(@NonNull String mStudiedTill) {
        this.mStudiedTill = mStudiedTill;
    }

    @NonNull
    public String getCityOrCountry() {
        return mCityOrCountry;
    }

    public void setCityOrCountry(@NonNull String mCityOrCountry) {
        this.mCityOrCountry = mCityOrCountry;
    }

    @NonNull
    public String getSubcoursesOrTasks() {
        return mSubcoursesOrTasks;
    }

    public void setSubcoursesOrTasks(@NonNull String mSubcoursesOrTasks) {
        this.mSubcoursesOrTasks = mSubcoursesOrTasks;
    }

    @NonNull
    public String getUserId() {
        return userId;
    }

    public void setUserId(@NonNull String userId) {
        this.userId = userId;
    }
}
