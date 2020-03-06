package com.example.microsftlogin.TodoDatabase;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TodoViewModel extends AndroidViewModel {

    private TodoRepository todoRepository;
    private LiveData<List<Todo>> tAllTodos;

    public TodoViewModel(@NonNull Application application) {
        super(application);
        todoRepository = new TodoRepository(application);
        tAllTodos = todoRepository.getAllTodos();
    }


    public LiveData<List<Todo>> getAllTodos() { return tAllTodos; }

    public void insert(Todo todo) { todoRepository.insert(todo); }

    public void update(Todo todo) { todoRepository.update(todo); }

    public void delete(Todo todo) { todoRepository.delete(todo); }

}
