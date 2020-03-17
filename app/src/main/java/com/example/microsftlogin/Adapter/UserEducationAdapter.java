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
import com.example.microsftlogin.UserEducationDatabase.UserEducation;
import com.example.microsftlogin.UserEducationDatabase.UserEducationViewModel;
import com.example.microsftlogin.UserExperienceDatabase.UserExperience;

import java.util.List;

public class UserEducationAdapter extends RecyclerView.Adapter<UserEducationAdapter.UserEducationRecyclerHolder> {
    private List<UserEducation> userEducationList;
    private LayoutInflater mInflater;
    private UserEducationViewModel userEducationViewModel;

    public UserEducationAdapter(Context context, List<UserEducation> _userEducationList, UserEducationViewModel _userEducationViewModel) {
        mInflater = LayoutInflater.from(context);
        this.userEducationList = _userEducationList;
        this.userEducationViewModel = _userEducationViewModel;
    }

    class UserEducationRecyclerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView jobTitle;
        public TextView companyName;
        public TextView workedFrom;
        public TextView workedTill;
        public TextView companyAddress;
        public TextView tasksPerformed;
        public UserExperienceAdapter mAdapter;
        private ImageView editUserEducationBtn;
        private ImageView deleteUserEducationBtn;

       public UserEducationRecyclerHolder(@NonNull View itemView, UserEducationAdapter adapter) {
           super(itemView);
           jobTitle = itemView.findViewById(R.id.userexperience_jobTitle);
           companyName = itemView.findViewById(R.id.userexperience_companyName);
           workedFrom = itemView.findViewById(R.id.userexperience_list_worked_from);
           workedTill = itemView.findViewById(R.id.userexperience_list_worked_till);
           companyAddress = itemView.findViewById(R.id.userexperience_companyAddress);
           tasksPerformed = itemView.findViewById(R.id.userexperience_tasksPerformed);
           editUserEducationBtn = itemView.findViewById(R.id.edituserexperience_btn);
           deleteUserEducationBtn = itemView.findViewById(R.id.deleteuserexperience_btn);
           this.mAdapter = mAdapter;

           editUserEducationBtn.setOnClickListener(this);
           deleteUserEducationBtn.setOnClickListener(this);
       }

        @Override
        public void onClick(View v) {
           switch (v.getId()) {
               case R.id.edituserexperience_btn :
                   Bundle args = new Bundle();
                   args.putInt("ActionToPerform",2);
                   args.putInt("UserEducationPosition",getAdapterPosition());
                   args.putInt("UserEducationId",userEducationList.get(getAdapterPosition()).getId());
                   args.putString("CourseName",userEducationList.get(getAdapterPosition()).getCourseName());
                   args.putString("SchoolName",userEducationList.get(getAdapterPosition()).getSchoolOrWebsite());
                   args.putString("StudiedFrom",userEducationList.get(getAdapterPosition()).getStudiedFrom());
                   args.putString("StudiedTill",userEducationList.get(getAdapterPosition()).getStudiedTill());
                   args.putString("SchoolAddress",userEducationList.get(getAdapterPosition()).getCityOrCountry());
                   args.putString("SubCourses",userEducationList.get(getAdapterPosition()).getSubcoursesOrTasks());
                   Navigation.findNavController(v).navigate(R.id.addUserEducation,args);
               break;

               case R.id.deleteuserexperience_btn :
                   UserEducation currentEducation = getUserEducationAt(getAdapterPosition());
                   UserEducation userEducationToDelete = new UserEducation(currentEducation.getId(),
                           currentEducation.getCourseName(),currentEducation.getSchoolOrWebsite(),
                           currentEducation.getStudiedFrom(),currentEducation.getStudiedTill(),currentEducation.getCityOrCountry()
                           ,currentEducation.getSubcoursesOrTasks(),currentEducation.getUserId());

                   if (currentEducation != null) {
                       userEducationViewModel.delete(userEducationToDelete);
                       userEducationList.remove(getAdapterPosition());
                       notifyDataSetChanged();
                   }

               break;
           }
        }
    }

    @NonNull
    @Override
    public UserEducationAdapter.UserEducationRecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.single_userexpirience,parent,false);
        return new UserEducationRecyclerHolder(itemView,this);
    }

    @Override
    public void onBindViewHolder(@NonNull UserEducationAdapter.UserEducationRecyclerHolder holder, int position) {
        UserEducation mCurrent = userEducationList.get(position);

        holder.jobTitle.setText(mCurrent.getCourseName());
        holder.companyName.setText(mCurrent.getSchoolOrWebsite());
        holder.workedFrom.setText(mCurrent.getStudiedFrom());
        holder.workedTill.setText(mCurrent.getStudiedTill());
        holder.companyAddress.setText(mCurrent.getCityOrCountry());
        holder.tasksPerformed.setText(mCurrent.getSubcoursesOrTasks());
    }

    public UserEducation getUserEducationAt(int position) {
        if (userEducationList.size() > 0) {
            return userEducationList.get(position);
        } else {
            return null;
        }
    }

    @Override
    public int getItemCount() {
        if (userEducationList.size() > 0) {
            return userEducationList.size();
        } else {
            return 0;
        }
    }
}
