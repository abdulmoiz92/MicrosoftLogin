package com.example.microsftlogin.UserEducationDatabase;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.microsftlogin.UserDatabase.UserRoomDatabase;

import java.util.List;

public class UserEducationRepository {
    private UserEducationDao userEducationDao;
    private LiveData<List<UserEducation>> allUserEducations;

    public UserEducationRepository(Application application) {
        UserRoomDatabase db = UserRoomDatabase.getDatabase(application);
        userEducationDao = db.userEducationDao();
        allUserEducations = userEducationDao.getAllUserEducation();
    }

    public LiveData<List<UserEducation>> getAllUserEducations() { return allUserEducations; }

    public void insert(UserEducation userEducation) { new insertAsyncTask(userEducationDao).execute(userEducation); }

    public void update(UserEducation userEducation) { new updateAsyncTask(userEducationDao).execute(userEducation); }

    public void delete(UserEducation userEducation) { new deleteAsyncTask(userEducationDao).execute(userEducation); }

    private static class insertAsyncTask extends AsyncTask<UserEducation, Void, Void> {
        private UserEducationDao mAsyncTaskDao;

        insertAsyncTask(UserEducationDao userEducationDao) { this.mAsyncTaskDao =  userEducationDao; }

        @Override
        protected Void doInBackground(UserEducation... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<UserEducation, Void, Void> {
        private UserEducationDao mAsyncTaskDao;

        updateAsyncTask(UserEducationDao userEducationDao) { this.mAsyncTaskDao = userEducationDao; }

        @Override
        protected Void doInBackground(UserEducation... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<UserEducation,Void,Void> {
        private UserEducationDao mAsyncTaskDao;

        deleteAsyncTask(UserEducationDao userEducationDao) { this.mAsyncTaskDao = userEducationDao; }

        @Override
        protected Void doInBackground(UserEducation... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }
}
