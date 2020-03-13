package com.example.microsftlogin.UserEducationDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserEducationDao {
    @Query("SELECT * FROM user_education_table")
    LiveData<List<UserEducation>> getAllUserEducation();

    @Insert
    void insert(UserEducation userEducation);
}
