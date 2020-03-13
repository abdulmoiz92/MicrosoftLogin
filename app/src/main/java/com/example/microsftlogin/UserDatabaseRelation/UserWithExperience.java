package com.example.microsftlogin.UserDatabaseRelation;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.microsftlogin.UserDatabase.User;
import com.example.microsftlogin.UserExperienceDatabase.UserExperience;

import java.util.List;

public class UserWithExperience {
    @Embedded
    public User user;

    @Relation(
            parentColumn = "id",
            entityColumn = "experienceuser_id"
    )
    public List<UserExperience> userExperiences;

    public User getUser() {
        return user;
    }

    public List<UserExperience> getUserExperiences() {
        return userExperiences;
    }

    public boolean hasUserExperiences() {
        if (userExperiences.size() > 0) {
            return true;
        } else {
            return false;
        }
    }
}
