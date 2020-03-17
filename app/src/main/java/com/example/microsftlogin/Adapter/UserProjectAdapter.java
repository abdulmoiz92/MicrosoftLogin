package com.example.microsftlogin.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.microsftlogin.R;
import com.example.microsftlogin.UserProjectsDatabase.UserProject;

import java.util.List;

public class UserProjectAdapter extends RecyclerView.Adapter<UserProjectAdapter.UserProjectRecyclerHolder> {
    private List<UserProject> userProjectList;
    private LayoutInflater mInflater;

    public UserProjectAdapter(Context context, List<UserProject> userProjects) {
        mInflater = LayoutInflater.from(context);
        userProjectList = userProjects;
    }

    class UserProjectRecyclerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView singleProjectName;
        public TextView singleProjectTasks;
        final UserProjectAdapter mAdapter;
        public ImageView editProjectBtn;

        public UserProjectRecyclerHolder(@NonNull View itemView, UserProjectAdapter adapter) {
            super(itemView);
            singleProjectName = itemView.findViewById(R.id.userproject_projectname);
            singleProjectTasks = itemView.findViewById(R.id.userproject_tasksPerformed);
            this.mAdapter = adapter;
            editProjectBtn = itemView.findViewById(R.id.edituserproject_btn);

            editProjectBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.edituserproject_btn :

                break;
            }
        }
    }

    @NonNull
    @Override
    public UserProjectAdapter.UserProjectRecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.single_userproject,parent,false);
        return new UserProjectRecyclerHolder(mItemView,this);
    }

    @Override
    public void onBindViewHolder(@NonNull UserProjectAdapter.UserProjectRecyclerHolder holder, int position) {
        UserProject mCurrent = userProjectList.get(position);

        holder.singleProjectName.setText(mCurrent.getProjectName());
        holder.singleProjectTasks.setText(mCurrent.getProjectTasks());
    }

    public UserProject getProjectAt(int position) {
        if (userProjectList.size() > 0) {
            return userProjectList.get(position);
        } else {
            return null;
        }
    }

    public void deleteProject(int position) {
        userProjectList.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (userProjectList.size() > 0) {
            return userProjectList.size();
        } else {
            return 0;
        }
    }
}
