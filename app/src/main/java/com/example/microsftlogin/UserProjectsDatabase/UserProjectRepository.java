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

    private static class insertAsyncTask extends AsyncTask<UserProject, Void, Void> {
        private UserProjectDao mAsyncTaskDao;

        public insertAsyncTask(UserProjectDao userProjectDao) { this.mAsyncTaskDao = userProjectDao; }

        @Override
        protected Void doInBackground(UserProject... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
