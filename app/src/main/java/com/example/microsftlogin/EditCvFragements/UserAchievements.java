package com.example.microsftlogin.EditCvFragements;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.microsftlogin.Adapter.UserAchievementAdapter;
import com.example.microsftlogin.R;
import com.example.microsftlogin.UserAchievementsDatabase.UserAchievement;
import com.example.microsftlogin.UserAchievementsDatabase.UserAchievementViewModel;
import com.example.microsftlogin.UserDatabase.UserViewModel;
import com.example.microsftlogin.Utils.SharedPrefrenceUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserAchievements extends Fragment {
    private TextInputLayout addUserAchievementName;
    private TextInputLayout addUserAchievementDescription;
    private Button addUserAchievementSubmit;
    private UserAchievementViewModel userAchievementViewModel;
    private UserViewModel userViewModel;
    private RecyclerView mRecyclerView;
    private UserAchievementAdapter mAdapter;
    private List<UserAchievement> userAchievementList;

    public UserAchievements() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_achievements, container, false);

        final String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();

        addUserAchievementName = view.findViewById(R.id.adduserachievements_achievementname);
        addUserAchievementDescription = view.findViewById(R.id.adduserachievements_description);
        addUserAchievementSubmit = view.findViewById(R.id.adduserachievements_btn);

        userAchievementViewModel = ViewModelProviders.of(getActivity()).get(UserAchievementViewModel.class);
        userViewModel = ViewModelProviders.of(getActivity()).get(UserViewModel.class);

        userAchievementList = userViewModel.findUserWithAchievement(user_id).get(0).getUserAchievements();
        mAdapter = new UserAchievementAdapter(getActivity(),userAchievementList);
        mRecyclerView = view.findViewById(R.id.userachievements_recyclerview);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        final FirebaseUser currentuser = FirebaseAuth.getInstance().getCurrentUser();

        addUserAchievementSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!addUserAchievementName.getEditText().getText().toString().equals("") &&
                        !addUserAchievementDescription.getEditText().getText().toString().equals("")) {
                    DatabaseReference achRef = FirebaseDatabase.getInstance().getReference().child("users")
                            .child(currentuser.getUid()).child("achievements").push();
                    final UserAchievement userAchievementToAdd = new UserAchievement(achRef.getKey(),
                            addUserAchievementName.getEditText().getText().toString(),
                            addUserAchievementDescription.getEditText().getText().toString(),user_id);
                    achRef.setValue(userAchievementToAdd).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                userAchievementViewModel.insert(userAchievementToAdd);
                                mAdapter.addAchievement(userAchievementToAdd);
                                addUserAchievementName.getEditText().setText("");
                                addUserAchievementDescription.getEditText().setText("");
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(),"Something Went Wrong",Toast.LENGTH_SHORT).show();
                        }
                    });


                } else {
                    Toast.makeText(getActivity(),"All Fields Need To Be Filled",Toast.LENGTH_LONG).show();
                }
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
                final int position = viewHolder.getAdapterPosition();
                final UserAchievement userAchievementToDelete = mAdapter.getUserAchievementAt(position);
                DatabaseReference achRef = FirebaseDatabase.getInstance().getReference().child("users")
                        .child(currentuser.getUid()).child("achievements").child(userAchievementToDelete.getId());
                achRef.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        userAchievementViewModel.delete(userAchievementToDelete);
                        mAdapter.deleteAchievement(position);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(),"Something Went Wrong",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        helper.attachToRecyclerView(mRecyclerView);

        return view;
    }
}
