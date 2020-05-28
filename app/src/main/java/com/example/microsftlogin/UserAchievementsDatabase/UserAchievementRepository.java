package com.example.microsftlogin.UserAchievementsDatabase;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.microsftlogin.UserDatabase.UserRoomDatabase;

import java.util.List;

public class UserAchievementRepository {
    private UserAchievementDao userAchievementDao;
    private LiveData<List<UserAchievement>> allUserAchievements;

    public UserAchievementRepository(Application application) {
        UserRoomDatabase db = UserRoomDatabase.getDatabase(application);
        userAchievementDao = db.userAchievementDao();
        allUserAchievements = userAchievementDao.getAllUserAchievements();
    }

    public LiveData<List<UserAchievement>> getAllUserAchievements() { return allUserAchievements; }

    public void insert(UserAchievement userAchievement) { new insertAsyncTask(userAchievementDao).execute(userAchievement); }

    public void delete(UserAchievement userAchievement) { new deleteAsyncTask(userAchievementDao).execute(userAchievement); }

    public void deleteAllUserAchievements() { new deleteAllAsyncTask(userAchievementDao).execute(); }

    private static class insertAsyncTask extends AsyncTask<UserAchievement,Void,Void> {
        private UserAchievementDao mAysnTaskDao;

        public insertAsyncTask(UserAchievementDao userAchievementDao) { this.mAysnTaskDao = userAchievementDao; }

        @Override
        protected Void doInBackground(UserAchievement... params) {
            mAysnTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<UserAchievement,Void,Void> {
        private UserAchievementDao mAsyncTaskDao;

        public deleteAsyncTask(UserAchievementDao userAchievementDao) { this.mAsyncTaskDao = userAchievementDao; }

        @Override
        protected Void doInBackground(UserAchievement... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

    private static class deleteAllAsyncTask extends AsyncTask<Void,Void,Void> {
        private UserAchievementDao mAsyncTaskDao;

        deleteAllAsyncTask(UserAchievementDao userAchievementDao) { this.mAsyncTaskDao = userAchievementDao; }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAllUserAchievements();
            return null;
        }
    }
}
