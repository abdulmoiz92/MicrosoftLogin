package com.example.microsftlogin.UserDatabaseRelation;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.microsftlogin.UserAchievementsDatabase.UserAchievement;
import com.example.microsftlogin.UserDatabase.User;

import java.util.List;

public class UserWithAchievement {
    @Embedded
    public User user;

    @Relation(
            parentColumn = "id",
            entityColumn = "achievementuser_id"
    )
    public List<UserAchievement> userAchievements;

    public User getUser() { return user; }

    public List<UserAchievement> getUserAchievements() { return userAchievements; }
}
