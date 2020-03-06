package com.example.microsftlogin.TodoDatabase;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class TodoRepository {
    private TodoDao todoDao;
    private LiveData<List<Todo>> allTodos;

    TodoRepository(Application application) {
        TodoRoomDatabase db = TodoRoomDatabase.getDatabase(application);
        todoDao = db.todoDao();
        allTodos = todoDao.getAllTodos();
    }

    public LiveData<List<Todo>> getAllTodos() { return allTodos; }

    public void insert(Todo todo) {
        new insertAsyncTask(todoDao).execute(todo);
    }

    public void update(Todo todo) { new updateAsyncTask(todoDao).execute(todo); }

    public void delete(Todo todo) { new deleteTodoAsyncTask(todoDao).execute(todo); }

    private static class insertAsyncTask extends AsyncTask<Todo,Void,Void> {
        private TodoDao mAsyncTaskDao;

        insertAsyncTask(TodoDao todoDao) {
            mAsyncTaskDao = todoDao;
        }

        @Override
        protected Void doInBackground(Todo... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<Todo,Void,Void> {
        private TodoDao mAsyncTaskDao;

        updateAsyncTask(TodoDao todoDao) { mAsyncTaskDao = todoDao; }

        @Override
        protected Void doInBackground(Todo... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }

    private static class deleteTodoAsyncTask extends AsyncTask<Todo, Void, Void> {
        private TodoDao mAsyncTaskDao;

        deleteTodoAsyncTask(TodoDao todoDao) { mAsyncTaskDao = todoDao; }

        @Override
        protected Void doInBackground(Todo... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }
}
