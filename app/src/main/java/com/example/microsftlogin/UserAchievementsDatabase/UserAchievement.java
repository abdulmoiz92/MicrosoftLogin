package com.example.microsftlogin.UserAchievementsDatabase;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_achievement_table")
public class UserAchievement {

    @NonNull
    @PrimaryKey
    private String id;

    @NonNull
    @ColumnInfo(name = "achievement_name")
    private String mAchievementName;

    @NonNull
    @ColumnInfo(name = "achievement_description")
    private String mAchievementDescription;

    @NonNull
    @ColumnInfo(name = "achievementuser_id")
    private String userId;

    public UserAchievement(String id,String mAchievementName, String mAchievementDescription, String userId) {
        this.id = id;
        this.mAchievementName = mAchievementName;
        this.mAchievementDescription = mAchievementDescription;
        this.userId = userId;
    }

    @Ignore
    public UserAchievement() {
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
