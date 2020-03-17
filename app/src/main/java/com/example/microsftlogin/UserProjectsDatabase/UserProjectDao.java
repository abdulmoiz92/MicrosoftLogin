package com.example.microsftlogin.UserProjectsDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserProjectDao {
    @Query("SELECT * FROM user_project_table")
    LiveData<List<UserProject>> getAllUserProject();

    @Insert
    void insert(UserProject userProject);
}
