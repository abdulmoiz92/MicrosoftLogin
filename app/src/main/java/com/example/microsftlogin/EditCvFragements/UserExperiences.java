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
import android.widget.Toast;

import com.example.microsftlogin.Adapter.UserExperienceAdapter;
import com.example.microsftlogin.R;
import com.example.microsftlogin.UserDatabase.UserViewModel;
import com.example.microsftlogin.UserDatabaseRelation.UserWithExperience;
import com.example.microsftlogin.UserExperienceDatabase.UserExperience;
import com.example.microsftlogin.UserExperienceDatabase.UserExperienceViewModel;
import com.example.microsftlogin.Utils.SharedPrefrenceUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserExperiences extends Fragment {
    private FloatingActionButton addUserExperience;
    private NavController navController;
    private UserViewModel userViewModel;
    private RecyclerView mRecyclerView;
    private UserExperienceAdapter mAdapter;
   // private List<UserWithExperience> userWithExperienceList = new ArrayList<>();
    private List<UserExperience> userExperienceList = new ArrayList<>();
    String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private UserExperienceViewModel userExperienceViewModel;

    public UserExperiences() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_user_experiences, container, false);
        addUserExperience = view.findViewById(R.id.add_userexperience_btn);
        navController = Navigation.findNavController(getActivity(), R.id.fragment);
        addUserExperience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.addUserExperience);
            }
        });

        userExperienceViewModel= ViewModelProviders.of(getActivity()).get(UserExperienceViewModel.class);

        userViewModel = ViewModelProviders.of(getActivity()).get(UserViewModel.class);
        if (userViewModel.findUserWithExperiences(user_id).size() > 0) {
            userExperienceList = userViewModel.findUserWithExperiences(user_id).get(0).getUserExperiences();
        }
        mAdapter = new UserExperienceAdapter(getActivity(),userExperienceList,userExperienceViewModel);
        mRecyclerView = view.findViewById(R.id.userexperience_recyclerView);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));

        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        final int position = viewHolder.getAdapterPosition();
                        if (direction == ItemTouchHelper.LEFT) {
                            UserExperience currentExperience = mAdapter.getUserExperienceAt(position);
                            final UserExperience userExperienceToDelete = new UserExperience(currentExperience.getId(),
                                    currentExperience.getJobTitle(),currentExperience.getCompanyName(),
                                    currentExperience.getWorkedFrom(),currentExperience.getWorkedTill(),currentExperience.getCityOrCountry(),
                                    currentExperience.getTasksPerformed(),currentExperience.getUserId());
                            if (currentExperience != null) {
                                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                                DatabaseReference curExp = FirebaseDatabase.getInstance().getReference().child("users")
                                        .child(currentUser.getUid()).child("experiences").child(currentExperience.getId());
                                curExp.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        userExperienceViewModel.delete(userExperienceToDelete);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getActivity(), "Something Went Wrong Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        } else if (direction == ItemTouchHelper.RIGHT) {
                            Bundle args = new Bundle();
                            args.putInt("ActionToPerform",2);
                            args.putInt("UserExperiencePosition",position);
                            args.putString("UserExperienceId",mAdapter.getUserExperienceAt(position).getId());
                            args.putString("JobTitle",mAdapter.getUserExperienceAt(position).getJobTitle());
                            args.putString("CompanyName",mAdapter.getUserExperienceAt(position).getCompanyName());
                            args.putString("WorkedFrom",mAdapter.getUserExperienceAt(position).getWorkedFrom());
                            args.putString("WorkedTill",mAdapter.getUserExperienceAt(position).getWorkedTill());
                            args.putString("CompanyAddress",mAdapter.getUserExperienceAt(position).getCityOrCountry());
                            args.putString("TasksPerformed",mAdapter.getUserExperienceAt(position).getTasksPerformed());
                            Navigation.findNavController(view).navigate(R.id.addUserExperience,args);
                        }
                    }
                }
        );

        helper.attachToRecyclerView(mRecyclerView);

        return view;
    }
}
