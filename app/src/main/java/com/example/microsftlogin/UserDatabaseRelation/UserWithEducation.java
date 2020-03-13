package com.example.microsftlogin.UserDatabaseRelation;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.microsftlogin.UserDatabase.User;
import com.example.microsftlogin.UserEducationDatabase.UserEducation;

import java.util.List;

public class UserWithEducation {
    @Embedded
    public User user;

    @Relation(
            parentColumn = "id",
            entityColumn = "educationuser_id"
    )
    public List<UserEducation> userEducations;

    public User getUser() {
        return user;
    }

    public List<UserEducation> getUserEducations() {
        return userEducations;
    }

    public boolean hasUserEducation() {
        if (userEducations.size() > 0) {
            return true;
        } else {
            return false;
        }
    }
}
