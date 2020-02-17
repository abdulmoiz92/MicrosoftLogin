package com.example.microsftlogin.dashboardsActivities;


import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.microsftlogin.Adapter.Todo;
import com.example.microsftlogin.Adapter.TodoAdapter;
import com.example.microsftlogin.Helpers.DatepickerFragment;
import com.example.microsftlogin.Helpers.SharedPrefrenceHelper;
import com.example.microsftlogin.R;
import com.example.microsftlogin.TabsFragment.Dashboard;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class TodolistFragment extends Fragment {


    SharedPrefrenceHelper sph = new SharedPrefrenceHelper();
    List<Todo> todos = new ArrayList<>();
    List<Todo> todosFromPref = new ArrayList<>();
    private ListView listview;
    private TodoAdapter todoAdapter;
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
        sph.tprefrences = getActivity().getSharedPreferences(sph.getTodoSharedfile(),MODE_PRIVATE);
        if(null != sph.getSpArray(SharedPrefrenceHelper.getTodo_Key(), todos))
        todosFromPref = sph.getSpArray(SharedPrefrenceHelper.getTodo_Key(), todos);


        listview = view.findViewById(R.id.TaskList);
        floatingbtn = view.findViewById(R.id.fab);

        floatingbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                AddTodoFragment addTodoFragment = new AddTodoFragment();
                fragmentTransaction.replace(R.id.main_content, addTodoFragment);
                fragmentTransaction.commit();
            }
        });

        if(null != todosFromPref && todosFromPref.size() > 0){
             todoAdapter = new TodoAdapter(getActivity(), todosFromPref);

        } else {
            todoAdapter = new TodoAdapter(getActivity(), todos);
        }

        listview.setAdapter(todoAdapter);

        return view;
    }
}
