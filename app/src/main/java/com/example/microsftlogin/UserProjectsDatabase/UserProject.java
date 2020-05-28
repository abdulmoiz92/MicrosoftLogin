package com.example.microsftlogin.UserProjectsDatabase;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_project_table")
public class UserProject {

    @NonNull
    @PrimaryKey
    private String id;

    @NonNull
    @ColumnInfo(name = "project_name")
    private String mProjectName;

    @NonNull
    @ColumnInfo(name = "project_tasks")
    private String mProjectTasks;

    @NonNull
    @ColumnInfo(name = "projectuser_id")
    private String userId;


    public UserProject(String id, String mProjectName, String mProjectTasks, String userId) {
        this.id = id;
        this.mProjectName = mProjectName;
        this.mProjectTasks = mProjectTasks;
        this.userId = userId;
    }

    @Ignore
    public UserProject() {
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

    @NonNull
    public String getUserId() {
        return userId;
    }

    public void setUserId(@NonNull String userId) {
        this.userId = userId;
    }
}
