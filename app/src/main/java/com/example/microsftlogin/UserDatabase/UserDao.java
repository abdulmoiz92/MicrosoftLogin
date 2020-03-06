package com.example.microsftlogin.UserDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(User user);

    @Query("SELECT * from user_table ORDER BY id DESC")
    LiveData<List<User>> getAllUser();

    @Query("SELECT * from user_table LIMIT 1")
    User[] getAnyUser();

    @Query("SELECT * FROM user_table WHERE user_email = :userEmail")
    LiveData<List<User>> find(String userEmail);
}
