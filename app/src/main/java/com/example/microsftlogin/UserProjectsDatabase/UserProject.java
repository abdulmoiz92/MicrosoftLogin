package com.example.microsftlogin.UserProjectsDatabase;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_project_table")
public class UserProject {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "project_name")
    private String mProjectName;

    @NonNull
    @ColumnInfo(name = "project_tasks")
    private String mProjectTasks;

    @NonNull
    @ColumnInfo(name = "projectuser_id")
    private int userId;

    public UserProject(String mProjectName, String mProjectTasks, int userId) {
        this.mProjectName = mProjectName;
        this.mProjectTasks = mProjectTasks;
        this.userId = userId;
    }

    @Ignore
    public UserProject(int id, String mProjectName, String mProjectTasks, int userId) {
        this.id = id;
        this.mProjectName = mProjectName;
        this.mProjectTasks = mProjectTasks;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getProjectName() {
        return mProjectName;
    }

    public void setProjectName(@NonNull String mProjectName) {
        this.mProjectName = mProjectName;
    }

    @NonNull
    public String getProjectTasks() {
        return mProjectTasks;
    }

    public void setProjectTasks(@NonNull String mProjectTasks) {
        this.mProjectTasks = mProjectTasks;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
