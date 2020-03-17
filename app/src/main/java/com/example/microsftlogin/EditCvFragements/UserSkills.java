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
import com.google.android.material.textfield.TextInputLayout;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_user_skills, container, false);

        final int user_id = SharedPrefrenceUtil.getInstance(getActivity()).getIntValue(SharedPrefrenceUtil.CURRENT_USER_ID);

        addUserSkillName = view.findViewById(R.id.adduserskill_skillname);
        addUserSkillSubmit = view.findViewById(R.id.adduserskill_btn);
        userSkillViewModel = ViewModelProviders.of(getActivity()).get(UserSkillViewModel.class);
        userViewModel = ViewModelProviders.of(getActivity()).get(UserViewModel.class);

        userSkillList = userViewModel.findUserWithSkill(user_id).get(0).getUserSkills();
        mAdapter = new UserSkillAdapter(getActivity(),userSkillList);
        mRecyclerView = view.findViewById(R.id.userskills_recyclerView);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));

        addUserSkillSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!addUserSkillName.getEditText().getText().toString().equals("") ) {
                    UserSkill userSkillToAdd = new UserSkill(addUserSkillName.getEditText().getText().toString(),user_id);
                    userSkillViewModel.insert(userSkillToAdd);
                    mAdapter.addSkill(userSkillToAdd);
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
                        int position = viewHolder.getAdapterPosition();
                        UserSkill skill = mAdapter.getSkillAt(position);
                        userSkillViewModel.delete(skill);
                        mAdapter.deleteSkill(position);
                    }
                }
        );

        helper.attachToRecyclerView(mRecyclerView);

        return view;
    }
}
