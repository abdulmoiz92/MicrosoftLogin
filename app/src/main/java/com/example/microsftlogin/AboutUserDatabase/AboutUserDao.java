package com.example.microsftlogin.AboutUserDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.microsftlogin.UserDatabase.User;

import java.util.List;

@Dao
public interface AboutUserDao {

    @Query("SELECT * from about_user_table")
    LiveData<List<AboutUser>> getAllAboutUser();

    @Insert
    void insert(AboutUser aboutUser);

    @Update
    void update(AboutUser aboutUser);



}
