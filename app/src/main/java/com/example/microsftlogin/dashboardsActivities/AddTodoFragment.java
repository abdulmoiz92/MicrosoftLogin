package com.example.microsftlogin.dashboardsActivities;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.microsftlogin.Adapter.TodoRecyclerAdapter;
import com.example.microsftlogin.TodoDatabase.Todo;
import com.example.microsftlogin.Adapter.TodoAdapter;
import com.example.microsftlogin.Helpers.DatepickerFragment;
import com.example.microsftlogin.Helpers.SharedPrefrenceHelper;
import com.example.microsftlogin.R;
import com.example.microsftlogin.TodoDatabase.TodoViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddTodoFragment extends Fragment {


    EditText editTodo;
    Button addTask;
    Button editDate;
    TextView date;
    List<Todo> todos = new ArrayList<>();
    List<Todo> todosFromPref = new ArrayList<>();
    SharedPrefrenceHelper sph = new SharedPrefrenceHelper();
    private TodoViewModel todoViewModel;

   // private TodoAdapter todoAdapter;


    public AddTodoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_todo, container, false);
        todoViewModel = ViewModelProviders.of(getActivity()).get(TodoViewModel.class);
        final TodoRecyclerAdapter adapter = new TodoRecyclerAdapter(getActivity(),todoViewModel);

        todoViewModel.getAllTodos().observe(getActivity(), new Observer<List<Todo>>() {
            @Override
            public void onChanged(List<Todo> todos) {
                // Update the cached copy of the words in the adapter.
                adapter.setTodos(todos);
            }
        });

  /*      sph.tprefrences = getActivity().getSharedPreferences(sph.getTodoSharedfile(), Context.MODE_PRIVATE);
        if(null != sph.getSpArray(SharedPrefrenceHelper.getTodo_Key(), todos))
            todosFromPref = sph.getSpArray(SharedPrefrenceHelper.getTodo_Key(), todos); */

        editTodo = view.findViewById(R.id.addtodo_task);
        editDate = view.findViewById(R.id.addtodo_datebtn);
        date = view.findViewById(R.id.addtodo_date);
        addTask = view.findViewById(R.id.addtodo_btn);

        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment DateFragment = new DatepickerFragment();
                DateFragment.show(getFragmentManager(),"DatePicker");
            }
        });
        
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTodo.length() > 0 && date.length() > 0) {
                    Todo newTodo = new Todo(editTodo.getText().toString(), date.getText().toString());
                    todoViewModel.insert(newTodo);
                    /*
                    todosFromPref.add(new Todo(editTodo.getText().toString(), date.getText().toString()));
                    sph.editSpArray(SharedPrefrenceHelper.getTodo_Key(), todosFromPref);
                    todoAdapter.notifyDataSetChanged(); */
                    Toast.makeText(getActivity(), "Task Has Been Added", Toast.LENGTH_LONG).show();
                    Navigation.findNavController(v).navigate(AddTodoFragmentDirections.actionAddTodoFragmentToTodolistFragment());
                } else {
                    Toast.makeText(getActivity(),"All Fields Need To Be Filled",Toast.LENGTH_LONG).show();
                }
            }
        });


      /*  if(null != todosFromPref && todosFromPref.size() > 0){
            todoAdapter = new TodoAdapter(getActivity(), todosFromPref);

        } else {
            todoAdapter = new TodoAdapter(getActivity(), todos);
        } */

        return view;

    }

}
