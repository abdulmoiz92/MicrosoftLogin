package com.example.microsftlogin.TodoDatabase;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "todo_table")
public class Todo {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "todo")
    private String mTask;

    @NonNull
    @ColumnInfo(name = "tododate")
    private String mDate;


    public Todo(@NonNull String task, @NonNull String date) {
        this.mTask = task;
        this.mDate = date;
    }

    @Ignore
    public Todo(int id, String task, String date) {
        this.id = id;
        this.mTask = task;
        this.mDate = date;
    }

    public String getTask() {
        return mTask;
    }

    public void setTask(String mTask) {
        this.mTask = mTask;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String mdate) {
        this.mDate = mdate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
