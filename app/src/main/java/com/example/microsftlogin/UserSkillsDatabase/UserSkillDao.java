package com.example.microsftlogin.UserSkillsDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.microsftlogin.UserEducationDatabase.UserEducation;

import java.util.List;

@Dao
public interface UserSkillDao {
    @Query("SELECT * FROM user_skill_table")
    LiveData<List<UserSkill>> getAllUserSkills();

    @Insert
    void insert(UserSkill userSkill);

    @Delete
    void delete(UserSkill userSkill);

    @Query("DELETE from user_skill_table")
        void deleteAllUserSkill();
}
