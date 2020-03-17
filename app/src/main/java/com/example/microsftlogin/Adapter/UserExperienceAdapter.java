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
import com.example.microsftlogin.UserExperienceDatabase.UserExperience;
import com.example.microsftlogin.UserExperienceDatabase.UserExperienceViewModel;

import java.util.List;

public class UserExperienceAdapter extends RecyclerView.Adapter<UserExperienceAdapter.UserInfoRecyclerHolder> {
    private List<UserExperience> userexperiencesArrayList;
    private LayoutInflater mInflater;
    private UserExperienceViewModel userExperienceViewModel;


    public UserExperienceAdapter(Context context, List<UserExperience> userExperienceList, UserExperienceViewModel _userExperienceViewModel) {
        mInflater = LayoutInflater.from(context);
        this.userexperiencesArrayList = userExperienceList;
        this.userExperienceViewModel = _userExperienceViewModel;
    }

    class UserInfoRecyclerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // User Experience Variable
        public TextView jobTitle;
        public TextView companyName;
        public TextView workedFrom;
        public TextView workedTill;
        public TextView companyAddress;
        public TextView tasksPerformed;
        public UserExperienceAdapter mAdapter;
        public ImageView editUserExperiencebtn;
        public ImageView deleteUserExperiencebtn;


        public UserInfoRecyclerHolder(@NonNull View itemView, UserExperienceAdapter mAdapter) {
            super(itemView);
            jobTitle = itemView.findViewById(R.id.userexperience_jobTitle);
            companyName = itemView.findViewById(R.id.userexperience_companyName);
            workedFrom = itemView.findViewById(R.id.userexperience_list_worked_from);
            workedTill = itemView.findViewById(R.id.userexperience_list_worked_from);
            companyAddress = itemView.findViewById(R.id.userexperience_companyAddress);
            tasksPerformed = itemView.findViewById(R.id.userexperience_tasksPerformed);
            editUserExperiencebtn = itemView.findViewById(R.id.edituserexperience_btn);
            deleteUserExperiencebtn = itemView.findViewById(R.id.deleteuserexperience_btn);
            this.mAdapter = mAdapter;

            editUserExperiencebtn.setOnClickListener(this);
            deleteUserExperiencebtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.edituserexperience_btn:
                    Bundle args = new Bundle();
                    args.putInt("ActionToPerform",2);
                    args.putInt("UserExperiencePosition",getAdapterPosition());
                    args.putInt("UserExperienceId",userexperiencesArrayList.get(getAdapterPosition()).getId());
                    args.putString("JobTitle",userexperiencesArrayList.get(getAdapterPosition()).getJobTitle());
                    args.putString("CompanyName",userexperiencesArrayList.get(getAdapterPosition()).getCompanyName());
                    args.putString("WorkedFrom",userexperiencesArrayList.get(getAdapterPosition()).getWorkedFrom());
                    args.putString("WorkedTill",userexperiencesArrayList.get(getAdapterPosition()).getWorkedTill());
                    args.putString("CompanyAddress",userexperiencesArrayList.get(getAdapterPosition()).getCityOrCountry());
                    args.putString("TasksPerformed",userexperiencesArrayList.get(getAdapterPosition()).getTasksPerformed());
                    Navigation.findNavController(v).navigate(R.id.addUserExperience,args);
                break;

                case R.id.deleteuserexperience_btn:
                    UserExperience currentExperience = getUserExperienceAt(getAdapterPosition());
                    UserExperience userExperienceToDelete = new UserExperience(currentExperience.getId(),
                            currentExperience.getJobTitle(),currentExperience.getCompanyName(),
                            currentExperience.getWorkedFrom(),currentExperience.getWorkedTill(),currentExperience.getCityOrCountry(),
                            currentExperience.getTasksPerformed(),currentExperience.getUserId());
                    if (currentExperience != null) {
                        mAdapter.userExperienceViewModel.delete(userExperienceToDelete);
                        userexperiencesArrayList.remove(getAdapterPosition());
                        notifyDataSetChanged();
                    } else {

                    }
                break;
            }

        }
    }

    public UserExperience getUserExperienceAt(int position) {
        if (userexperiencesArrayList.size() > 0) {
            return userexperiencesArrayList.get(position);
        } else {
            return null;
        }
    }

    @NonNull
    @Override
    public UserExperienceAdapter.UserInfoRecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.single_userexpirience,parent,false);
        return new UserInfoRecyclerHolder(mItemView,this);
    }

    @Override
    public void onBindViewHolder(@NonNull UserExperienceAdapter.UserInfoRecyclerHolder holder, int position) {
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
