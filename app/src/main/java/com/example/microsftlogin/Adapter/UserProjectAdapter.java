package com.example.microsftlogin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
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

    class UserProjectRecyclerHolder extends RecyclerView.ViewHolder {
        public TextView singleProjectName;
        public TextView singleProjectTasks;
        final UserProjectAdapter mAdapter;

        public UserProjectRecyclerHolder(@NonNull View itemView, UserProjectAdapter adapter) {
            super(itemView);
            singleProjectName = itemView.findViewById(R.id.userproject_projectname);
            singleProjectTasks = itemView.findViewById(R.id.userproject_tasksPerformed);
            this.mAdapter = adapter;
        }
    }

    @NonNull
    @Override
    public UserProjectAdapter.UserProjectRecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.single_userproject,parent);
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull UserProjectAdapter.UserProjectRecyclerHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
