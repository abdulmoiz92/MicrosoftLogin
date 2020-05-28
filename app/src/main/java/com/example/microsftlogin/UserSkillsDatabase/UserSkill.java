package com.example.microsftlogin.UserSkillsDatabase;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_skill_table")
public class UserSkill {

    @NonNull
    @PrimaryKey
    private String id;

    @NonNull
    @ColumnInfo(name = "skill_name")
    private String mSkillName;

    @NonNull
    @ColumnInfo(name = "skilluser_id")
    private String userId;

    public UserSkill(String id, String mSkillName, String userId) {
        this.id = id;
        this.mSkillName = mSkillName;
        this.userId = userId;
    }

    @Ignore
    public UserSkill() {
        //For Datasnapshots
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @NonNull
    public String getSkillName() {
        return mSkillName;
    }

    public void setSkillName(@NonNull String mSkillName) {
        this.mSkillName = mSkillName;
    }

    @NonNull
    public String getUserId() {
        return userId;
    }

    public void setUserId(@NonNull String userId) {
        this.userId = userId;
    }
}
