package com.example.microsftlogin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.microsftlogin.R;
import com.example.microsftlogin.UserExperienceDatabase.UserExperience;

import java.util.List;

public class UserInfoAdapter extends RecyclerView.Adapter<UserInfoAdapter.UserInfoRecyclerHolder> {
    private List<UserExperience> userexperiencesArrayList;
    private LayoutInflater mInflater;


    class UserInfoRecyclerHolder extends RecyclerView.ViewHolder {
        public TextView jobTitle;
        public TextView companyName;
        public TextView workedFrom;
        public TextView workedTill;
        public TextView companyAddress;
        public TextView tasksPerformed;
        public UserInfoAdapter mAdapter;

        public UserInfoRecyclerHolder(@NonNull View itemView, UserInfoAdapter mAdapter) {
            super(itemView);
            jobTitle = itemView.findViewById(R.id.userexperience_jobTitle);
            companyName = itemView.findViewById(R.id.userexperience_companyName);
            workedFrom = itemView.findViewById(R.id.userexperience_list_worked_from);
            workedTill = itemView.findViewById(R.id.userexperience_list_worked_from);
            companyAddress = itemView.findViewById(R.id.userexperience_companyAddress);
            tasksPerformed = itemView.findViewById(R.id.userexperience_tasksPerformed);
            this.mAdapter = mAdapter;
        }
    }

   public UserInfoAdapter(Context context, List<UserExperience> userExperienceList) {
        mInflater = LayoutInflater.from(context);
        this.userexperiencesArrayList = userExperienceList;
    }

    public void setUserExperiences(List<UserExperience> userExperiences) {
        userexperiencesArrayList = userExperiences;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserInfoAdapter.UserInfoRecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.single_userexpirience,parent,false);
        return new UserInfoRecyclerHolder(mItemView,this);
    }

    @Override
    public void onBindViewHolder(@NonNull UserInfoAdapter.UserInfoRecyclerHolder holder, int position) {
        UserExperience mCurrent = userexperiencesArrayList.get(position);

        holder.jobTitle.setText(mCurrent.getJobTitle());
        holder.companyName.setText(mCurrent.getCompanyName());
        holder.workedFrom.setText(mCurrent.getWorkedFrom());
        holder.workedTill.setText(mCurrent.getWorkedTill());
        holder.companyAddress.setText(mCurrent.getCityOrCountry());
        holder.tasksPerformed.setText(mCurrent.getTasksPerformed());
    }

    @Override
    public int getItemCount() {
       if (userexperiencesArrayList.size() > 0) {
           return userexperiencesArrayList.size();
       } else {
           return 0;
       }
    }
}
