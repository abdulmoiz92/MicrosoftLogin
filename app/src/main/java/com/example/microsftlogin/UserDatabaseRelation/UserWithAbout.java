package com.example.microsftlogin.UserDatabaseRelation;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.microsftlogin.AboutUserDatabase.AboutUser;
import com.example.microsftlogin.UserDatabase.User;

import java.util.List;

public class UserWithAbout {
    @Embedded
    public User user;

    @Relation(
            parentColumn = "id",
            entityColumn = "aboutuser_id"
    )
    public List<AboutUser> aboutUser;

    public User getUser() {
        return user;
    }

    public List<AboutUser> getAboutUserList() {
        return aboutUser;
    }

    public AboutUser getAboutUser() {
        return aboutUser.get(0);
    }

    public boolean hasAboutUser() {
        if (aboutUser.size() > 0 ) {
            return true;
        } else {
            return false;
        }
    }
}
