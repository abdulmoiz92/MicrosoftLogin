package com.example.microsftlogin.UserDatabase;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.microsftlogin.UserDatabaseRelation.UserWithAbout;
import com.example.microsftlogin.UserDatabaseRelation.UserWithExperience;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * from user_table")
    LiveData<List<User>> getAllUser();

    @Insert
    void insert(User user);

    @Query("SELECT * from user_table LIMIT 1")
    User[] getAnyUser();

    @Query("SELECT * FROM user_table WHERE user_email =:userEmail")
    List<User> find(String userEmail);

    @Query("SELECT * FROM user_table WHERE id = :userId")
    List<User> find(int userId);

    @Transaction
    @Query("SELECT * FROM user_table")
    LiveData<List<UserWithAbout>> getAllUsersWithAbout();

    @Transaction
    @Query("SELECT * FROM user_table WHERE id = :userId")
    List<UserWithAbout> findUserWithAbout(int userId);

    @Transaction
    @Query("SELECT * FROM user_table")
    LiveData<List<UserWithExperience>> getAllUserWithExperience();

    @Transaction
    @Query("SELECT * FROM user_table WHERE id = :userId")
    List<UserWithExperience> findUserWithExperience(int userId);
}
