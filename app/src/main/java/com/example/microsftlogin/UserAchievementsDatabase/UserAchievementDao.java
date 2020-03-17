package com.example.microsftlogin.UserAchievementsDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserAchievementDao {
    @Query("SELECT * FROM user_achievement_table")
    LiveData<List<UserAchievement>> getAllUserAchievements();

    @Insert
    void insert(UserAchievement userAchievement);

    @Delete
    void delete(UserAchievement userAchievement);
}
