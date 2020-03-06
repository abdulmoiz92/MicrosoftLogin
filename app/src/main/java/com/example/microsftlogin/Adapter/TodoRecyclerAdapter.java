package com.example.microsftlogin.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.microsftlogin.Helpers.SharedPrefrenceHelper;
import com.example.microsftlogin.R;
import com.example.microsftlogin.TabsFragment.Dashboard;
import com.example.microsftlogin.TodoDatabase.Todo;
import com.example.microsftlogin.TodoDatabase.TodoViewModel;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class TodoRecyclerAdapter extends RecyclerView.Adapter<TodoRecyclerAdapter.TodoRecyclerHolder> {
    private List<Todo> todoArrayList;
   // SharedPrefrenceHelper sph = new SharedPrefrenceHelper();
    private LayoutInflater layoutInflater;
    private int viewId;
    private TodoViewModel todoViewModel;



    class TodoRecyclerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView todo;
        public final TextView date;
        public final TodoRecyclerAdapter madpter;
        public ImageView deletetodobtn;
        public ImageView edittodobtn;

        public TodoRecyclerHolder(@NonNull View itemView, TodoRecyclerAdapter adapter) {
            super(itemView);
            todo = itemView.findViewById(R.id.TodoText);
            date = itemView.findViewById(R.id.dateText);
            this.madpter = adapter;
            deletetodobtn = itemView.findViewById(R.id.deletetodo_btn);
            edittodobtn = itemView.findViewById(R.id.edittodo_btn);

            if (deletetodobtn != null) {
                deletetodobtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                   /* sph.tprefrences = v.getContext().getSharedPreferences(sph.getTodoSharedfile(), MODE_PRIVATE);
                    sph.editSpArray(SharedPrefrenceHelper.getTodo_Key(), todoArrayList); */

                        Todo currentTodo = getTodoAtPosition(getAdapterPosition());
                        Todo todoToDelete = new Todo(currentTodo.getId(), currentTodo.getTask(), currentTodo.getDate());
                        todoViewModel.delete(currentTodo);
                        todoArrayList.remove(getAdapterPosition());
                        notifyDataSetChanged();

                    }
                });

            }


            if (edittodobtn != null) {
                edittodobtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle args = new Bundle();
                        args.putString("Todo", todoArrayList.get(getAdapterPosition()).getTask());
                        args.putString("Date", todoArrayList.get(getAdapterPosition()).getDate());
                        args.putInt("position", getAdapterPosition());
                        Navigation.findNavController(v).navigate(R.id.editTodoFragment, args);
                    }
                });
            }
        }

        @Override
        public void onClick(View v) {
        }
    }

    public TodoRecyclerAdapter(Context context, List<Todo> Todolist, int viewNumber) {
        layoutInflater = LayoutInflater.from(context);
        this.todoArrayList = Todolist;
        this.viewId = viewNumber;
    }

    public TodoRecyclerAdapter(Context context, TodoViewModel _todoViewModel, int viewNumber) {
        layoutInflater = LayoutInflater.from(context);
        this.todoViewModel = _todoViewModel;
        this.viewId = viewNumber;
    }

    public TodoRecyclerAdapter(Context context,TodoViewModel _todoViewModel) {
        layoutInflater = LayoutInflater.from(context);
        this.todoViewModel = _todoViewModel;
    }

    public void setTodos(List<Todo> todos) {
        todoArrayList = todos;
        notifyDataSetChanged();
    }

    public Todo getTodoAtPosition(int position) {
        if (todoArrayList.size() > 0) {
            return todoArrayList.get(position);
        } else {
          return null;
        }
    }

    @NonNull
    @Override
    public TodoRecyclerAdapter.TodoRecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView;
        if (viewId == 1 ) {
            mItemView = layoutInflater.inflate(R.layout.activity_list_item, parent, false);
        } else {
            mItemView = layoutInflater.inflate(R.layout.activity_slider_list_item, parent, false);
        }

        return new TodoRecyclerHolder(mItemView,this);
    }

    @Override
    public void onBindViewHolder(@NonNull final TodoRecyclerAdapter.TodoRecyclerHolder holder, final int position) {
       Todo mCurrent = todoArrayList.get(position);

       holder.todo.setText(mCurrent.getTask());
       holder.date.setText(mCurrent.getDate());
    }

    @Override
    public int getItemCount() {
        if (todoArrayList != null) {
            return todoArrayList.size();
        } else {
            return 0;
        }
    }
}
