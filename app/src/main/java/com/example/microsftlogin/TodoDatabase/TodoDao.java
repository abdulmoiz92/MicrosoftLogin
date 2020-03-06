package com.example.microsftlogin.TodoDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Todo todo);

    @Query("SELECT * from todo_table ORDER BY id DESC")
    LiveData<List<Todo>> getAllTodos();

    @Query("SELECT * from todo_table LIMIT 1")
    Todo[] getAnyTodo();

    @Update
    void update(Todo todo);

    @Delete
    void delete(Todo todo);
}
