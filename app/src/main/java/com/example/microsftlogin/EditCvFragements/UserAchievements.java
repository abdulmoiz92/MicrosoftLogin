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
import com.google.android.material.textfield.TextInputLayout;

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

        final int user_id = SharedPrefrenceUtil.getInstance(getActivity()).getIntValue(SharedPrefrenceUtil.CURRENT_USER_ID);

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

        addUserAchievementSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!addUserAchievementName.getEditText().getText().toString().equals("") &&
                        !addUserAchievementDescription.getEditText().getText().toString().equals("")) {
                    UserAchievement userAchievementToAdd = new UserAchievement(
                            addUserAchievementName.getEditText().getText().toString(),
                            addUserAchievementDescription.getEditText().getText().toString(),user_id);

                    userAchievementViewModel.insert(userAchievementToAdd);
                    mAdapter.addAchievement(userAchievementToAdd);
                    addUserAchievementName.getEditText().setText("");
                    addUserAchievementDescription.getEditText().setText("");
                } else {
                    Toast.makeText(getActivity(),"All Fields Need To Be FIlled",Toast.LENGTH_LONG).show();
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
                UserAchievement userAchievementToDelete = mAdapter.getUserAchievementAt(position);
                userAchievementViewModel.delete(userAchievementToDelete);
                mAdapter.deleteAchievement(position);
            }
        });

        helper.attachToRecyclerView(mRecyclerView);

        return view;
    }
}
