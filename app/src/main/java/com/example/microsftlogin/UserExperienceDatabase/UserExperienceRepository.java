package com.example.microsftlogin.UserExperienceDatabase;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.microsftlogin.UserDatabase.UserRoomDatabase;

import java.util.List;

public class UserExperienceRepository {
    private UserExperienceDao userExperienceDao;
    private LiveData<List<UserExperience>> allUserExperience;

    UserExperienceRepository(Application application) {
        UserRoomDatabase db = UserRoomDatabase.getDatabase(application);
        userExperienceDao = db.userExperienceDao();
        allUserExperience = userExperienceDao.getAllUserExperience();
    }

    public LiveData<List<UserExperience>> getAllUserExperience() { return allUserExperience; }

    public void insert(UserExperience userExperience) { new insertAsyncTask(userExperienceDao).execute(userExperience); }

    public void update(UserExperience userExperience) { new updateAsyncTask(userExperienceDao).execute(userExperience); }

    public void delete(UserExperience userExperience) { new deleteAsyncTask(userExperienceDao).execute(userExperience); }

    public void deleteAllUserExperience() { new deleteAllAysncTask(userExperienceDao).execute(); }

    private static class insertAsyncTask extends AsyncTask<UserExperience,Void,Void> {
        private UserExperienceDao mAsyncTaskDao;

        insertAsyncTask(UserExperienceDao userExperienceDao) { mAsyncTaskDao = userExperienceDao; }


        @Override
        protected Void doInBackground(UserExperience... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<UserExperience,Void,Void> {
        private UserExperienceDao mAsyncTaskDao;

        updateAsyncTask(UserExperienceDao userExperienceDao) { mAsyncTaskDao = userExperienceDao; }

        @Override
        protected Void doInBackground(UserExperience... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<UserExperience,Void,Void> {
        private UserExperienceDao mAsyncTaskDao;

        deleteAsyncTask(UserExperienceDao userExperienceDao) { this.mAsyncTaskDao = userExperienceDao; }

        @Override
        protected Void doInBackground(UserExperience... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

    private static class deleteAllAysncTask extends AsyncTask<Void,Void,Void> {
        private UserExperienceDao mAsyncTaskDao;

        deleteAllAysncTask(UserExperienceDao userExperienceDao) { this.mAsyncTaskDao = userExperienceDao; }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAllUserExperience();
            return null;
        }
    }
}
