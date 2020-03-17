package com.example.microsftlogin.UserDatabase;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.microsftlogin.UserDatabaseRelation.UserWithAbout;
import com.example.microsftlogin.UserDatabaseRelation.UserWithAchievement;
import com.example.microsftlogin.UserDatabaseRelation.UserWithEducation;
import com.example.microsftlogin.UserDatabaseRelation.UserWithExperience;
import com.example.microsftlogin.UserDatabaseRelation.UserWithProject;
import com.example.microsftlogin.UserDatabaseRelation.UserWithSkill;

import java.util.List;

public class UserRepository {
    private UserDao userDao;
    private LiveData<List<User>> allUsers;
    private LiveData<List<UserWithAbout>> allUsersWithAbout;
    private LiveData<List<UserWithExperience>> allUsersWithExperience;
    private LiveData<List<UserWithEducation>> allUsersWithEducation;
    private LiveData<List<UserWithSkill>> allUsersWithSkill;
    private LiveData<List<UserWithProject>> allUsersWithProjects;
    private LiveData<List<UserWithAchievement>> allUsersWithAchievements;

    UserRepository(Application application) {
        UserRoomDatabase db = UserRoomDatabase.getDatabase(application);
        userDao = db.userDao();
        allUsers = userDao.getAllUser();
        allUsersWithAbout = userDao.getAllUsersWithAbout();
        allUsersWithExperience = userDao.getAllUserWithExperience();
        allUsersWithEducation = userDao.getAllUserWithEducation();
        allUsersWithSkill = userDao.getAllUserWithSkill();
        allUsersWithProjects = userDao.getAllUserWithProject();
        allUsersWithAchievements = userDao.getAllUserWithAchievement();
    }


    public LiveData<List<User>> getAllUsers() { return allUsers; }

    // User With About

    public LiveData<List<UserWithAbout>> getAllUsersWithAbout() { return allUsersWithAbout; }

    public List<UserWithAbout> findUserWithAbout(int id) { return userDao.findUserWithAbout(id); }

    // User With Experience
    public LiveData<List<UserWithExperience>> getAllUsersWithExperience() { return allUsersWithExperience; }

    public List<UserWithExperience> findUserWithExperience(int id) { return userDao.findUserWithExperience(id); }

    // User With Education
    public LiveData<List<UserWithEducation>> getAllUserWithEducation() { return allUsersWithEducation; }

    public List<UserWithEducation> findUserWithEducation(int id) { return userDao.findUserWithEducation(id); }

    // User With Skill
    public LiveData<List<UserWithSkill>> getAllUserWithSkill() { return allUsersWithSkill; }
    public List<UserWithSkill> findUserWithSkill(int id) { return userDao.findUserWithSkill(id); }

    // User With Project
    public LiveData<List<UserWithProject>> getAllUsersWithProjects() { return allUsersWithProjects; }
    public List<UserWithProject> findUserWithProject(int id) { return userDao.findUserWithProject(id); }


    // User With Achievement
    public LiveData<List<UserWithAchievement>> getAllUsersWithAchievements() { return allUsersWithAchievements; }
    public List<UserWithAchievement> findUserWithAchievement(int id) { return userDao.findUserWithAchievement(id); }

    // User Functions

    public void insert(User user) { new insertAsyncTask(userDao).execute(user); }

    public List<User> findUser(String email) { return userDao.find(email); }

    public List<User> findUser(int id) { return userDao.find(id); }

    private static class insertAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao mAsyncTaskDao;

        insertAsyncTask(UserDao userDao) { this.mAsyncTaskDao = userDao; }

        @Override
        protected Void doInBackground(User... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
