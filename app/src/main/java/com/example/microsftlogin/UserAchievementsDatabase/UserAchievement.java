package com.example.microsftlogin.UserAchievementsDatabase;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_achievement_table")
public class UserAchievement {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "achievement_name")
    private String mAchievementName;

    @NonNull
    @ColumnInfo(name = "achievement_description")
    private String mAchievementDescription;

    @NonNull
    @ColumnInfo(name = "achievementuser_id")
    private int userId;

    public UserAchievement(String mAchievementName, String mAchievementDescription, int userId) {
        this.mAchievementName = mAchievementName;
        this.mAchievementDescription = mAchievementDescription;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getAchievementName() {
        return mAchievementName;
    }

    public void setAchievementName(@NonNull String mAchievementName) {
        this.mAchievementName = mAchievementName;
    }

    @NonNull
    public String getAchievementDescription() {
        return mAchievementDescription;
    }

    public void setAchievementDescription(@NonNull String mAchievementDescription) {
        this.mAchievementDescription = mAchievementDescription;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
