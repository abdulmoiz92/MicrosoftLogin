package com.example.microsftlogin.UserDatabaseRelation;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.microsftlogin.UserDatabase.User;
import com.example.microsftlogin.UserProjectsDatabase.UserProject;

import java.util.List;

public class UserWithProject {
    @Embedded
    public User user;

    @Relation(
            parentColumn = "id",
            entityColumn = "projectuser_id"
    )
    public List<UserProject> userProjects;

    public User getUser() { return user; }

    public List<UserProject> getUserProjects() { return userProjects; }
}
