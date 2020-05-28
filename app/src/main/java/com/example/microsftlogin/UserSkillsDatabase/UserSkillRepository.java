package com.example.microsftlogin.UserSkillsDatabase;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.microsftlogin.UserDatabase.UserRoomDatabase;

import java.util.List;

public class UserSkillRepository {
    private UserSkillDao userSkillDao;
    private LiveData<List<UserSkill>> allUserSkills;

    public UserSkillRepository(Application application) {
        UserRoomDatabase db = UserRoomDatabase.getDatabase(application);
        userSkillDao = db.userSkillDao();
        allUserSkills = userSkillDao.getAllUserSkills();
    }

    public LiveData<List<UserSkill>> getAllUserSkills() { return allUserSkills; }

    public void insert(UserSkill userSkill) { new insertAyncTask(userSkillDao).execute(userSkill); }

    public void delete(UserSkill userSkill) { new deleteAsyncTask(userSkillDao).execute(userSkill); }

    public void deleteAllUserSkill() { new deleteAllAsyncTask(userSkillDao).execute(); }

    private static class insertAyncTask extends AsyncTask<UserSkill,Void,Void> {
        private UserSkillDao mAsyncTaskDao;

        public insertAyncTask(UserSkillDao userSkillDao) { this.mAsyncTaskDao = userSkillDao; }

        @Override
        protected Void doInBackground(UserSkill... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    public static class deleteAsyncTask extends AsyncTask<UserSkill,Void,Void> {
        private UserSkillDao mAsyncTaskDao;

        public deleteAsyncTask(UserSkillDao userSkillDao) { this.mAsyncTaskDao = userSkillDao; }


        @Override
        protected Void doInBackground(UserSkill... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

    public static class deleteAllAsyncTask extends AsyncTask<Void,Void,Void> {
        private UserSkillDao mAsyncTaskDao;

        deleteAllAsyncTask(UserSkillDao userSkillDao) { this.mAsyncTaskDao = userSkillDao; }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAllUserSkill();
            return null;
        }
    }
}
