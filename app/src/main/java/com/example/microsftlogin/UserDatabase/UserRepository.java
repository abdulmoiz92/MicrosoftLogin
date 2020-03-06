package com.example.microsftlogin.UserDatabase;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class UserRepository {
    private UserDao userDao;
    private LiveData<List<User>> allUsers;
   // private LiveData<List<User>> searchResults;

    UserRepository(Application application) {
        UserRoomDatabase db = UserRoomDatabase.getDatabase(application);
        userDao = db.userDao();
        allUsers = userDao.getAllUser();

    }


    public LiveData<List<User>> getAllUsers() { return allUsers; }

    public void insert(User user) { new insertAsyncTask(userDao).execute(user); }

    public LiveData<List<User>> findUser(String email) { return userDao.find(email); }

    /*
    public LiveData<List<User>> getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(LiveData<List<User>> searchResults) {
        this.searchResults = searchResults;
    } */

    private static class insertAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao mAsyncTaskDao;

        insertAsyncTask(UserDao userDao) { this.mAsyncTaskDao = userDao; }

        @Override
        protected Void doInBackground(User... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    /*
    private static class findAsyncTask extends AsyncTask<String, Void, LiveData<List<User>>> {
        private UserDao mAsyncTaskDao;
        UserRepository delegate = null;

        findAsyncTask(UserDao userDao) { this.mAsyncTaskDao = userDao; }


        @Override
        protected LiveData<List<User>> doInBackground(String... strings) {
            return mAsyncTaskDao.find(strings[0]);
        }

        @Override
        protected void onPostExecute(LiveData<List<User>> listLiveData) {
            super.onPostExecute(listLiveData);
            delegate.setSearchResults(listLiveData);
        }
    } */
}
