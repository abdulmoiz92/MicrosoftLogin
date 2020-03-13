package com.example.microsftlogin.EditCvFragements;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.microsftlogin.AboutUserDatabase.AboutUser;
import com.example.microsftlogin.AboutUserDatabase.AboutUserViewModel;
import com.example.microsftlogin.R;
import com.example.microsftlogin.UserDatabase.User;
import com.example.microsftlogin.UserDatabase.UserViewModel;
import com.example.microsftlogin.UserDatabaseRelation.UserWithAbout;
import com.example.microsftlogin.Utils.SharedPrefrenceUtil;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutUserFragment extends Fragment {
    private TextInputLayout aboutuserName;
    private TextInputLayout aboutuserEmail;
    private TextInputLayout aboutuserPhone;
    private TextInputLayout aboutuserAddress;
    private TextInputLayout aboutuserEducation;
    private TextInputLayout aboutuserDescription;
    private AboutUserViewModel aboutUserViewModel;
    private UserViewModel userViewModel;
    private List<AboutUser> mAllAboutUsers = new ArrayList<>();
    private List<UserWithAbout> userWithAboutList = new ArrayList<>();
    private Button addAboutUser_btn;

    public AboutUserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about_user, container, false);
        aboutuserName = view.findViewById(R.id.aboutuser_name);
        aboutuserEmail = view.findViewById(R.id.aboutuser_email);
        aboutuserPhone = view.findViewById(R.id.aboutuser_phone);
        aboutuserAddress = view.findViewById(R.id.aboutuser_address);
        aboutuserEducation = view.findViewById(R.id.aboutuser_degree);
        aboutuserDescription = view.findViewById(R.id.aboutuser_description);
        addAboutUser_btn = view.findViewById(R.id.aboutuser_submit);




        addAboutUser_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int user_id = SharedPrefrenceUtil.getInstance(getActivity()).getIntValue(SharedPrefrenceUtil.CURRENT_USER_ID);
                List<UserWithAbout> usersAboutInfoWithId = new ArrayList<>();
                usersAboutInfoWithId = userViewModel.findUserWithAbout(user_id);
                List<AboutUser> foundAboutUsersList = usersAboutInfoWithId.get(0).getAboutUserList();
                boolean hasAboutUser = usersAboutInfoWithId.get(0).hasAboutUser();

                //Fields
                String name = aboutuserName.getEditText().getText().toString();
                String email = aboutuserEmail.getEditText().getText().toString();
                String phone = aboutuserPhone.getEditText().getText().toString();
                String address = aboutuserAddress.getEditText().getText().toString();
                String education = aboutuserEducation.getEditText().getText().toString();
                String description = aboutuserDescription.getEditText().getText().toString();


                if (!hasAboutUser && foundAboutUsersList.size() < 1) {

                    if (!name.equals("") && !email.equals("")) {
                        AboutUser newAboutUserInfo = new AboutUser(name, email, phone, address, education, description, user_id);
                        aboutUserViewModel.insert(newAboutUserInfo);
                        Toast.makeText(getActivity(),"Information About You Added Successfully",Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getActivity(),"Name & Email Need To Be Filled",Toast.LENGTH_LONG).show();
                    }
                } else if (usersAboutInfoWithId.size() > 0 && mAllAboutUsers.size() > 0 && hasAboutUser && foundAboutUsersList.size() > 0) {
                    if (!name.equals("") && !email.equals("")) {
                        AboutUser aboutUserToUpdate = usersAboutInfoWithId.get(0).getAboutUser();
                        AboutUser newAboutUserInfo = new AboutUser(aboutUserToUpdate.getId(),name, email, phone, address, education, description, user_id);
                        aboutUserViewModel.update(newAboutUserInfo);
                        Toast.makeText(getActivity(),"Information Has Been Updated",Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getActivity(),"Name & Email Need To Be Filled",Toast.LENGTH_LONG).show();
                    }
                }

            }
        });

        return view;
    }

   public void onResume() {
        super.onResume();

       aboutUserViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(AboutUserViewModel.class);
       aboutUserViewModel.getAllAboutUsers().observe(getActivity(), new Observer<List<AboutUser>>() {
           @Override
           public void onChanged(List<AboutUser> aboutUsers) {
               mAllAboutUsers = aboutUsers;
           }
       });

       userViewModel = ViewModelProviders.of(getActivity()).get(UserViewModel.class);
       userViewModel.getAllUsersWithAbout().observe(getActivity(), new Observer<List<UserWithAbout>>() {
           @Override
           public void onChanged(List<UserWithAbout> userWithAbouts) {
               userWithAboutList = userWithAbouts;
           }
       });

       int user_id = SharedPrefrenceUtil.getInstance(getActivity()).getIntValue(SharedPrefrenceUtil.CURRENT_USER_ID);


       if (mAllAboutUsers.size() > 0) {
           List<UserWithAbout> usersAboutWithId;
           usersAboutWithId = userViewModel.findUserWithAbout(user_id);
           List<AboutUser> foundAboutUserList = usersAboutWithId.get(0).getAboutUserList();
           AboutUser foundAboutUser = null;
           if (foundAboutUserList.size() > 0) {
               foundAboutUser = usersAboutWithId.get(0).getAboutUser();
           }
           User foundUser = usersAboutWithId.get(0).getUser();
           aboutuserName.getEditText().setText(foundUser.getName());
           aboutuserEmail.getEditText().setText(foundUser.getEmail());


           if (usersAboutWithId.size() > 0 && null != foundAboutUser) {
               aboutuserName.getEditText().setText(foundAboutUser.getName());
               aboutuserEmail.getEditText().setText(foundAboutUser.getEmail());
               aboutuserPhone.getEditText().setText(foundAboutUser.getPhone());
               aboutuserAddress.getEditText().setText(foundAboutUser.getAddress());
               aboutuserEducation.getEditText().setText(foundAboutUser.getEducationDegree());
               aboutuserDescription.getEditText().setText(foundAboutUser.getDescription());

           }
       } else {
           List<User> currentUser;
           currentUser = userViewModel.findUser(user_id);
           if (currentUser.size() > 0) {
               aboutuserName.getEditText().setText(currentUser.get(0).getName());
               aboutuserEmail.getEditText().setText(currentUser.get(0).getEmail());
           }
       }
    }
}
