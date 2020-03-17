package com.example.microsftlogin.UserDatabaseRelation;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.microsftlogin.UserDatabase.User;
import com.example.microsftlogin.UserSkillsDatabase.UserSkill;

import java.util.List;

public class UserWithSkill {
    @Embedded
    public User user;

    @Relation(
            parentColumn = "id",
            entityColumn = "skilluser_id"
    )
   public List<UserSkill> userSkills;

    public User getUser() {
        return user;
    }

    public List<UserSkill> getUserSkills() {
        return userSkills;
    }
}
