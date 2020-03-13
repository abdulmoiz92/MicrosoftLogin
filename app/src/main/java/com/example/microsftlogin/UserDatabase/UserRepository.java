package com.example.microsftlogin.UserDatabase;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.microsftlogin.UserDatabaseRelation.UserWithAbout;
import com.example.microsftlogin.UserDatabaseRelation.UserWithExperience;

import java.util.List;

public class UserRepository {
    private UserDao userDao;
    private LiveData<List<User>> allUsers;
    private LiveData<List<UserWithAbout>> allUsersWithAbout;
    private LiveData<List<UserWithExperience>> allUsersWithExperience;

    UserRepository(Application application) {
        UserRoomDatabase db = UserRoomDatabase.getDatabase(application);
        userDao = db.userDao();
        allUsers = userDao.getAllUser();
        allUsersWithAbout = userDao.getAllUsersWithAbout();
        allUsersWithExperience = userDao.getAllUserWithExperience();
    }


    public LiveData<List<User>> getAllUsers() { return allUsers; }

    // User With About

    public LiveData<List<UserWithAbout>> getAllUsersWithAbout() { return allUsersWithAbout; }

    public List<UserWithAbout> findUserWithAbout(int id) { return userDao.findUserWithAbout(id); }

    // User With Experience
    public LiveData<List<UserWithExperience>> getAllUsersWithExperience() { return allUsersWithExperience; }

    public List<UserWithExperience> findUserWithExperience(int id) { return userDao.findUserWithExperience(id); }



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


  /* private static class findAsyncTask extends AsyncTask<String, Void, LiveData<List<User>>> {
        private UserDao mAsyncTaskDao;

        findAsyncTask(UserDao userDao) { this.mAsyncTaskDao = userDao; }

       @Override
       protected LiveData<List<User>> doInBackground(String... strings) {
           return mAsyncTaskDao.find(strings[0]);
       }
   } */
}
