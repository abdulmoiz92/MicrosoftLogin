package com.example.microsftlogin.TabsFragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.NavGraphNavigator;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
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
import com.example.microsftlogin.dashboardsActivities.EditTodoFragmentDirections;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class Dashboard extends Fragment implements View.OnClickListener {

    SharedPrefrenceHelper sph = new SharedPrefrenceHelper();
   // List<Todo> todos = new ArrayList<>();
   // List<Todo> todosFromPref = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private TodoRecyclerAdapter mAdapter;
    private TodoViewModel todoViewModel;
    private NavController navController;
    private MaterialCardView aboutuserCard;
    private MaterialCardView userexperienceCard;
    private MaterialCardView userEducationCard;
    private MaterialCardView userSkillsCard;
    private MaterialCardView userProjectsCard;
    private MaterialCardView userAcheivementsCard;


    public Dashboard() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        aboutuserCard = view.findViewById(R.id.aboutuser);
        userexperienceCard = view.findViewById(R.id.userexperience);
        userEducationCard = view.findViewById(R.id.usereducation);
        userSkillsCard = view.findViewById(R.id.userskills);
        userProjectsCard = view.findViewById(R.id.userprojects);
        userAcheivementsCard = view.findViewById(R.id.userachievements);
        navController = Navigation.findNavController(getActivity(), R.id.fragment);
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


       aboutuserCard.setOnClickListener(this);
       userexperienceCard.setOnClickListener(this);
       userEducationCard.setOnClickListener(this);
       userSkillsCard.setOnClickListener(this);
       userProjectsCard.setOnClickListener(this);
       userAcheivementsCard.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.aboutuser:
                navController.navigate(R.id.aboutUserFragment);
            break;

            case R.id.userexperience:
               navController.navigate(R.id.userExperiences);
            break;

            case R.id.usereducation:
               navController.navigate(R.id.userEducation);
            break;

            case R.id.userskills:
                navController.navigate(R.id.userSkills);
            break;

            case R.id.userprojects:
                navController.navigate(R.id.userPersonalProjects);
            break;

            case R.id.userachievements:
                navController.navigate(R.id.userAchievements);
            break;
        }
    }
}
