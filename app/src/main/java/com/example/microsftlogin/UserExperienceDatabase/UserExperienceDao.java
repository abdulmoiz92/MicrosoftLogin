package com.example.microsftlogin.UserExperienceDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserExperienceDao {

    @Query("SELECT * from user_experience_table")
    LiveData<List<UserExperience>> getAllUserExperience();

    @Insert
    void insert(UserExperience userExperience);

    @Update
    void update(UserExperience userExperience);

    @Delete
    void delete(UserExperience userExperience);
}
