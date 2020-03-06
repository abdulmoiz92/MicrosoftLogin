package com.example.microsftlogin.TabsFragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
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
public class Dashboard extends Fragment {

    SharedPrefrenceHelper sph = new SharedPrefrenceHelper();
   // List<Todo> todos = new ArrayList<>();
   // List<Todo> todosFromPref = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private TodoRecyclerAdapter mAdapter;
    private TodoViewModel todoViewModel;


    public Dashboard() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
       /* sph.tprefrences = getActivity().getSharedPreferences(sph.getTodoSharedfile(),MODE_PRIVATE);
        if(null != sph.getSpArray(SharedPrefrenceHelper.getTodo_Key(), todos))
            todosFromPref = sph.getSpArray(SharedPrefrenceHelper.getTodo_Key(), todos); */
/*        todoViewModel = ViewModelProviders.of(getActivity()).get(TodoViewModel.class);
        mAdapter = new TodoRecyclerAdapter(getActivity(),todoViewModel,2);
        todoViewModel.getAllTodos().observe(getActivity(), new Observer<List<Todo>>() {
            @Override
            public void onChanged(List<Todo> todos) {
                // Update the cached copy of the words in the adapter.
                mAdapter.setTodos(todos);
            }
        });

        mRecyclerView = view.findViewById(R.id.slidertodolist);

       /* if(null != todosFromPref && todosFromPref.size() > 0){
            //todoAdapter = new TodoAdapter(getActivity(), todosFromPref);
            mAdapter = new TodoRecyclerAdapter(getActivity(),todosFromPref,2);

        } else {
            //todoAdapter = new TodoAdapter(getActivity(), todos);
            mAdapter = new TodoRecyclerAdapter(getActivity(), todos,2);
        } */

       /* mRecyclerView.setAdapter(mAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        mRecyclerView.setLayoutManager(linearLayoutManager); */

        return view;
    }

}
