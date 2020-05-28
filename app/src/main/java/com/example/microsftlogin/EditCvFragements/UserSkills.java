package com.example.microsftlogin.EditCvFragements;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.microsftlogin.Adapter.UserEducationAdapter;
import com.example.microsftlogin.Adapter.UserSkillAdapter;
import com.example.microsftlogin.R;
import com.example.microsftlogin.UserDatabase.UserViewModel;
import com.example.microsftlogin.UserDatabaseRelation.UserWithSkill;
import com.example.microsftlogin.UserSkillsDatabase.UserSkill;
import com.example.microsftlogin.UserSkillsDatabase.UserSkillViewModel;
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
public class UserSkills extends Fragment {

    private TextInputLayout addUserSkillName;
    private Button addUserSkillSubmit;
    private UserSkillViewModel userSkillViewModel;
    private UserViewModel userViewModel;
    private RecyclerView mRecyclerView;
    private UserSkillAdapter mAdapter;
    private List<UserSkill> userSkillList;

    public UserSkills() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_user_skills, container, false);

        final String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();

        addUserSkillName = view.findViewById(R.id.adduserskill_skillname);
        addUserSkillSubmit = view.findViewById(R.id.adduserskill_btn);
        userSkillViewModel = ViewModelProviders.of(getActivity()).get(UserSkillViewModel.class);
        userViewModel = ViewModelProviders.of(getActivity()).get(UserViewModel.class);

        userSkillList = userViewModel.findUserWithSkill(user_id).get(0).getUserSkills();
        mAdapter = new UserSkillAdapter(getActivity(),userSkillList);
        mRecyclerView = view.findViewById(R.id.userskills_recyclerView);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        final FirebaseUser currentuser = FirebaseAuth.getInstance().getCurrentUser();

        addUserSkillSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!addUserSkillName.getEditText().getText().toString().equals("") ) {
                    DatabaseReference skillRef = FirebaseDatabase.getInstance().getReference().child("users")
                            .child(currentuser.getUid()).child("skills").push();
                    final UserSkill userSkillToAdd = new UserSkill(skillRef.getKey(),addUserSkillName.getEditText().getText().toString(),user_id);
                    skillRef.setValue(userSkillToAdd).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                userSkillViewModel.insert(userSkillToAdd);
                                mAdapter.addSkill(userSkillToAdd);
                                addUserSkillName.getEditText().setText("");
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(getActivity(),"Skill Name Need To Be Filled",Toast.LENGTH_LONG).show();
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
                        final UserSkill skill = mAdapter.getSkillAt(position);
                        DatabaseReference skillRef = FirebaseDatabase.getInstance().getReference().child("users")
                                .child(currentuser.getUid()).child("skills").child(skill.getId());
                        skillRef.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                userSkillViewModel.delete(skill);
                                mAdapter.deleteSkill(position);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
        );

        helper.attachToRecyclerView(mRecyclerView);

        return view;
    }
}
