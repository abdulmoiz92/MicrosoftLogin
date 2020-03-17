package com.example.microsftlogin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.microsftlogin.R;
import com.example.microsftlogin.UserSkillsDatabase.UserSkill;

import java.util.List;

public class UserSkillAdapter extends RecyclerView.Adapter<UserSkillAdapter.UserSkillRecyclerHolder> {
    private List<UserSkill> userSkillList;
    private LayoutInflater mInflater;

    public UserSkillAdapter(Context context, List<UserSkill> userSkills) {
        mInflater = LayoutInflater.from(context);
        userSkillList = userSkills;
    }

    class UserSkillRecyclerHolder extends RecyclerView.ViewHolder {
        public Button userSkillName;
        final UserSkillAdapter mAdapter;

        public UserSkillRecyclerHolder(@NonNull View itemView, UserSkillAdapter adapter) {
            super(itemView);
            userSkillName = itemView.findViewById(R.id.userskill_name);
            this.mAdapter = adapter;
        }
    }

    @NonNull
    @Override
    public UserSkillAdapter.UserSkillRecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.single_userskill, parent, false);
        return new UserSkillRecyclerHolder(mItemView,this);
    }

    @Override
    public void onBindViewHolder(@NonNull UserSkillAdapter.UserSkillRecyclerHolder holder, int position) {
        UserSkill mCurrent = userSkillList.get(position);

        holder.userSkillName.setText(mCurrent.getSkillName());
    }

    public void addSkill(UserSkill userSkill) {
        userSkillList.add(userSkill);
        notifyItemInserted(userSkillList.indexOf(userSkill));
    }

    public void deleteSkill(int position) {
        userSkillList.remove(position);
        notifyDataSetChanged();
    }

    public UserSkill getSkillAt(int position) {
        if (userSkillList.size() > 0) {
            return userSkillList.get(position);
        } else {
            return null;
        }
    }

    public void setUserSkills(List<UserSkill> userSkills) {
        this.userSkillList = userSkills;
    }

    @Override
    public int getItemCount() {
        if (userSkillList.size() > 0) {
            return userSkillList.size();
        } else {
            return 0;
        }
    }
}
