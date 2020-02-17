package com.example.microsftlogin.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.microsftlogin.Helpers.SharedPrefrenceHelper;
import com.example.microsftlogin.R;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class TodoAdapter extends ArrayAdapter<Todo> {
    private List<Todo> todoArrayList;
    SharedPrefrenceHelper sph = new SharedPrefrenceHelper();

    public TodoAdapter(@NonNull Activity context, List<Todo> todosArraylist) {
        super(context, 0, todosArraylist);
        this.todoArrayList = todosArraylist;
    }

    public List<Todo> getTodoArrayList() {
        return todoArrayList;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View todoItemView = convertView;
        if (todoItemView == null) {
            todoItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.activity_list_item, parent, false);
        }

        ImageView deletebtn = todoItemView.findViewById(R.id.deleteTodo);

        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                todoArrayList.remove(position);
                sph.tprefrences = getContext().getSharedPreferences(sph.getTodoSharedfile(),MODE_PRIVATE);
                sph.editSpArray(SharedPrefrenceHelper.getTodo_Key(), todoArrayList);
                notifyDataSetChanged();
            }
        });

        Todo currentTodo = getItem(position);

        TextView TodoText = todoItemView.findViewById(R.id.TodoText);

        TodoText.setText(currentTodo.getmTask());

        TextView TodoDate = todoItemView.findViewById(R.id.dateText);

        TodoDate.setText(currentTodo.getMdate());

        return todoItemView;
    }
}
