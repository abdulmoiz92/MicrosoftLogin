package com.example.microsftlogin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.microsftlogin.R;
import com.example.microsftlogin.UserAchievementsDatabase.UserAchievement;

import java.util.List;

public class UserAchievementAdapter extends RecyclerView.Adapter<UserAchievementAdapter.UserAchievementRecyclerHolder> {
    private List<UserAchievement> userAchievementList;
    private LayoutInflater mInflater;

    public UserAchievementAdapter(Context context, List<UserAchievement> userAchievements) {
        mInflater = LayoutInflater.from(context);
        userAchievementList = userAchievements;
    }

    class UserAchievementRecyclerHolder extends RecyclerView.ViewHolder {
        public TextView singleAchievementName;
        public TextView singleAchievementDescription;
        final UserAchievementAdapter mAdapter;
        public UserAchievementRecyclerHolder(@NonNull View itemView, UserAchievementAdapter adapter) {
            super(itemView);
            singleAchievementName = itemView.findViewById(R.id.userproject_projectname);
            singleAchievementDescription = itemView.findViewById(R.id.userproject_tasksPerformed);
            this.mAdapter = adapter;
        }
    }

    @NonNull
    @Override
    public UserAchievementAdapter.UserAchievementRecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.single_userproject,parent,false);
        return new UserAchievementRecyclerHolder(mItemView,this);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAchievementAdapter.UserAchievementRecyclerHolder holder, int position) {
        UserAchievement mCurrent = userAchievementList.get(position);

        holder.singleAchievementName.setText(mCurrent.getAchievementName());
        holder.singleAchievementDescription.setText(mCurrent.getAchievementDescription());
    }

    public void addAchievement(UserAchievement userAchievement) {
        userAchievementList.add(userAchievement);
        notifyItemInserted(userAchievementList.indexOf(userAchievement));
    }

    public void deleteAchievement(int position) {
        userAchievementList.remove(position);
        notifyDataSetChanged();
    }

    public UserAchievement getUserAchievementAt(int position) {
        if (userAchievementList.size() > 0) {
         return userAchievementList.get(position);
        } else {
            return null;
        }
    }

    @Override
    public int getItemCount() {
        if (userAchievementList.size() > 0) {
            return userAchievementList.size();
        } else {
            return 0;
        }
    }
}
