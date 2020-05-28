package com.example.microsftlogin.UserProjectsDatabase;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.microsftlogin.UserDatabase.UserRoomDatabase;

import java.util.List;

public class UserProjectRepository {
    private UserProjectDao userProjectDao;
    private LiveData<List<UserProject>> allUserProjects;

    public UserProjectRepository(Application application) {
        UserRoomDatabase db = UserRoomDatabase.getDatabase(application);
        userProjectDao = db.userProjectDao();
        allUserProjects = userProjectDao.getAllUserProject();
    }

    public LiveData<List<UserProject>> getAllUserProjects() { return allUserProjects; }

    public void insert(UserProject userProject) { new insertAsyncTask(userProjectDao).execute(userProject); }

    public void update(UserProject userProject) { new updateAsyncTask(userProjectDao).execute(userProject); }

    public void delete(UserProject userProject) { new deleteAsyncTask(userProjectDao).execute(userProject); }

    public void deleteAllUserProjects() { new deleteAllAsyncTask(userProjectDao).execute(); }

    private static class insertAsyncTask extends AsyncTask<UserProject, Void, Void> {
        private UserProjectDao mAsyncTaskDao;

        public insertAsyncTask(UserProjectDao userProjectDao) { this.mAsyncTaskDao = userProjectDao; }

        @Override
        protected Void doInBackground(UserProject... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<UserProject,Void,Void> {
        private UserProjectDao mAsyncTaskDao;

        public updateAsyncTask(UserProjectDao userProjectDao) { this.mAsyncTaskDao = userProjectDao; }

        @Override
        protected Void doInBackground(UserProject... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<UserProject,Void,Void> {
        private UserProjectDao mAsyncTaskDao;

        public deleteAsyncTask(UserProjectDao userProjectDao) { this.mAsyncTaskDao = userProjectDao; }


        @Override
        protected Void doInBackground(UserProject... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

    private static class deleteAllAsyncTask extends AsyncTask<Void,Void,Void> {
        private UserProjectDao mAsyncTaskDao;

        deleteAllAsyncTask(UserProjectDao userProjectDao) { this.mAsyncTaskDao = userProjectDao; }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAllUserProjects();
            return null;
        }
    }
}
