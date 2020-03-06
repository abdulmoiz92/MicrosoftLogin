package com.example.microsftlogin.dashboardsActivities;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.microsftlogin.TodoDatabase.Todo;
import com.example.microsftlogin.Adapter.TodoRecyclerAdapter;
import com.example.microsftlogin.Helpers.SharedPrefrenceHelper;
import com.example.microsftlogin.R;
import com.example.microsftlogin.TodoDatabase.TodoViewModel;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class TodolistFragment extends Fragment {


    SharedPrefrenceHelper sph = new SharedPrefrenceHelper();
    List<Todo> todos = new ArrayList<>();
    List<Todo> todosFromPref = new ArrayList<>();
    //private ListView listview;
    //private TodoAdapter todoAdapter;
    private RecyclerView mRecyclerView;
    private TodoRecyclerAdapter mAdapter;
    private TodoViewModel todoViewModel;
    private View floatingbtn;

    public TodolistFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LayoutInflater lf = getActivity().getLayoutInflater();
        View view = lf.inflate(R.layout.fragment_todolist,container,false);
        todoViewModel = ViewModelProviders.of(getActivity()).get(TodoViewModel.class);
        mAdapter = new TodoRecyclerAdapter(getActivity(),todoViewModel,1);
        todoViewModel.getAllTodos().observe(getActivity(), new Observer<List<Todo>>() {
            @Override
            public void onChanged(List<Todo> todos) {
                // Update the cached copy of the words in the adapter.

                mAdapter.setTodos(todos);
            }
        });
       /* sph.tprefrences = getActivity().getSharedPreferences(sph.getTodoSharedfile(),MODE_PRIVATE);
        if(null != sph.getSpArray(SharedPrefrenceHelper.getTodo_Key(), todos))
        todosFromPref = sph.getSpArray(SharedPrefrenceHelper.getTodo_Key(), todos); */


        //listview = view.findViewById(R.id.TaskList);
        mRecyclerView = view.findViewById(R.id.TaskList);
        floatingbtn = view.findViewById(R.id.fab);

        floatingbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(
                        TodolistFragmentDirections.actionTodolistFragmentToAddTodoFragment());
            }
        });

       /* if(null != todosFromPref && todosFromPref.size() > 0){
             //todoAdapter = new TodoAdapter(getActivity(), todosFromPref);
            mAdapter = new TodoRecyclerAdapter(getActivity(),todosFromPref,1);

        } else {
            //todoAdapter = new TodoAdapter(getActivity(), todos);
            mAdapter = new TodoRecyclerAdapter(getActivity(), todos,1);
        } */

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));

        return view;
    }
}
