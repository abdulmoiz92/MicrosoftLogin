package com.example.microsftlogin.UserSkillsDatabase;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_skill_table")
public class UserSkill {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "skill_name")
    private String mSkillName;

    @NonNull
    @ColumnInfo(name = "skilluser_id")
    private int userId;

    public UserSkill(String mSkillName, int userId) {
        this.mSkillName = mSkillName;
        this.userId = userId;
    }

    @Ignore
    public UserSkill(int id, String mSkillName, int userId) {
        this.id = id;
        this.mSkillName = mSkillName;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getSkillName() {
        return mSkillName;
    }

    public void setSkillName(@NonNull String mSkillName) {
        this.mSkillName = mSkillName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
