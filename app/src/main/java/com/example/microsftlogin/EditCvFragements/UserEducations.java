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

import com.example.microsftlogin.Adapter.UserEducationAdapter;
import com.example.microsftlogin.R;
import com.example.microsftlogin.UserDatabase.UserViewModel;
import com.example.microsftlogin.UserEducationDatabase.UserEducation;
import com.example.microsftlogin.UserEducationDatabase.UserEducationViewModel;
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
public class UserEducations extends Fragment {
    private FloatingActionButton addUserEducation;
    private NavController navController;
    private UserViewModel userViewModel;
    private UserEducationViewModel userEducationViewModel;
    private RecyclerView mRecyclerView;
    private UserEducationAdapter mAdapter;
    private List<UserEducation> userEducationList = new ArrayList<>();
    String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();



    public UserEducations() {
        // Required empty public
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_user_education, container, false);
        addUserEducation = view.findViewById(R.id.add_usereducation_btn);
        navController = Navigation.findNavController(getActivity(), R.id.fragment);
        addUserEducation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.addUserEducation);
            }
        });

        userViewModel = ViewModelProviders.of(getActivity()).get(UserViewModel.class);
        userEducationViewModel = ViewModelProviders.of(getActivity()).get(UserEducationViewModel.class);
        if (userViewModel.findUserWithEducation(user_id).size() > 0) {
            userEducationList = userViewModel.findUserWithEducation(user_id).get(0).getUserEducations();
        }
        mAdapter = new UserEducationAdapter(getActivity(),userEducationList,userEducationViewModel);
        mRecyclerView = view.findViewById(R.id.usereducation_recyclerView);

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
                            UserEducation currentEducation = mAdapter.getUserEducationAt(position);
                            final UserEducation userEducationToDelete = new UserEducation(currentEducation.getId(),
                                    currentEducation.getCourseName(),currentEducation.getSchoolOrWebsite(),
                                    currentEducation.getStudiedFrom(),currentEducation.getStudiedTill(),currentEducation.getCityOrCountry()
                                    ,currentEducation.getSubcoursesOrTasks(),currentEducation.getUserId());

                            if (currentEducation != null) {
                                FirebaseUser currentuser = FirebaseAuth.getInstance().getCurrentUser();
                                DatabaseReference eduRef = FirebaseDatabase.getInstance().getReference().child("users")
                                        .child(currentuser.getUid()).child("educations").child(userEducationToDelete.getId());
                                eduRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            userEducationViewModel.delete(userEducationToDelete);
                                        }
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
                            args.putInt("UserEducationPosition",position);
                            args.putString("UserEducationId",mAdapter.getUserEducationAt(position).getId());
                            args.putString("CourseName",mAdapter.getUserEducationAt(position).getCourseName());
                            args.putString("SchoolName",mAdapter.getUserEducationAt(position).getSchoolOrWebsite());
                            args.putString("StudiedFrom",mAdapter.getUserEducationAt(position).getStudiedFrom());
                            args.putString("StudiedTill",mAdapter.getUserEducationAt(position).getStudiedTill());
                            args.putString("SchoolAddress",mAdapter.getUserEducationAt(position).getCityOrCountry());
                            args.putString("SubCourses",mAdapter.getUserEducationAt(position).getSubcoursesOrTasks());
                            Navigation.findNavController(view).navigate(R.id.addUserEducation,args);
                        }
                    }
                }
        );

        helper.attachToRecyclerView(mRecyclerView);

        return view;
    }
}
