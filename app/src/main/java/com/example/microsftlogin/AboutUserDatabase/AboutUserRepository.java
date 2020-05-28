package com.example.microsftlogin.AboutUserDatabase;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.microsftlogin.UserDatabase.UserRoomDatabase;

import java.util.List;

public class AboutUserRepository {
    private AboutUserDao aboutUserDao;
    private LiveData<List<AboutUser>> allAboutUser;

    AboutUserRepository(Application application) {
        UserRoomDatabase db = UserRoomDatabase.getDatabase(application);
        aboutUserDao = db.aboutUserDao();
        allAboutUser = aboutUserDao.getAllAboutUser();
    }

    public LiveData<List<AboutUser>> getAllAboutUser() { return allAboutUser; }

    public void insert(AboutUser aboutUser) { new insertAsyncTask(aboutUserDao).execute(aboutUser); }

    public void update(AboutUser aboutUser) { new updateAsyncTask(aboutUserDao).execute(aboutUser); }

    public void deleteAllAboutUser() { new deleteAllAsyncTask(aboutUserDao).execute(); }

    private static class insertAsyncTask extends AsyncTask<AboutUser,Void,Void> {
        private AboutUserDao mAsyncTaskDao;

        insertAsyncTask(AboutUserDao aboutUserDao) { mAsyncTaskDao = aboutUserDao; }

        @Override
        protected Void doInBackground(AboutUser... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<AboutUser,Void,Void> {
        private AboutUserDao mAsyncTaskDao;

        updateAsyncTask(AboutUserDao aboutUserDao) { mAsyncTaskDao = aboutUserDao; }

        @Override
        protected Void doInBackground(AboutUser... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }

    private static class deleteAllAsyncTask extends AsyncTask<Void,Void,Void> {
        private AboutUserDao mAysncTaskDao;

        deleteAllAsyncTask(AboutUserDao aboutUserDao) { mAysncTaskDao = aboutUserDao; }

        @Override
        protected Void doInBackground(Void... voids) {
            mAysncTaskDao.deleteAllAboutUser();
            return null;
        }
    }
}
