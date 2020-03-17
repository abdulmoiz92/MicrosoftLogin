package com.example.microsftlogin.EditCvFragements;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.microsftlogin.Adapter.UserProjectAdapter;
import com.example.microsftlogin.R;
import com.example.microsftlogin.UserDatabase.UserViewModel;
import com.example.microsftlogin.UserProjectsDatabase.UserProject;
import com.example.microsftlogin.UserProjectsDatabase.UserProjectViewModel;
import com.example.microsftlogin.Utils.SharedPrefrenceUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserPersonalProjects extends Fragment {
    private FloatingActionButton addUserProjects;
    private NavController navController;
    private UserViewModel userViewModel;
    private UserProjectViewModel userProjectViewModel;
    private RecyclerView mRecyclerView;
    private UserProjectAdapter mAdapter;
    private List<UserProject> userProjectList = new ArrayList<>();
    int user_id = SharedPrefrenceUtil.getInstance(getActivity()).getIntValue(SharedPrefrenceUtil.CURRENT_USER_ID);

    public UserPersonalProjects() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_personal_projects, container, false);
        addUserProjects = view.findViewById(R.id.add_userprojects_btn);
        navController = Navigation.findNavController(getActivity(), R.id.fragment);

        userViewModel = ViewModelProviders.of(getActivity()).get(UserViewModel.class);
        userProjectViewModel = ViewModelProviders.of(getActivity()).get(UserProjectViewModel.class);
        userProjectList = userViewModel.findUserWithProject(user_id).get(0).getUserProjects();
        mAdapter = new UserProjectAdapter(getActivity(),userProjectList);
        mRecyclerView = view.findViewById(R.id.userprojects_recycleview);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));

        addUserProjects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.addUserPersonalProjects);
            }
        });

        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                if (direction == ItemTouchHelper.LEFT) {
                    UserProject project = mAdapter.getProjectAt(position);
                    userProjectViewModel.delete(project);
                    mAdapter.deleteProject(position);
                } else if(direction == ItemTouchHelper.RIGHT) {
                    Bundle args = new Bundle();
                    args.putInt("ActionToPerform",2);
                    args.putInt("ProjectUserId",userProjectList.get(position).getId());
                    args.putString("ProjectName",userProjectList.get(position).getProjectName());
                    args.putString("ProjectTasks",userProjectList.get(position).getProjectTasks());
                    Navigation.findNavController(getActivity(),R.id.fragment).navigate(R.id.addUserPersonalProjects,args);
                }
            }
        });

        helper.attachToRecyclerView(mRecyclerView);

        return view;
    }
}
