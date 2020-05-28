package com.example.microsftlogin.EditCvFragements;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.microsftlogin.AboutUserDatabase.AboutUser;
import com.example.microsftlogin.AboutUserDatabase.AboutUserViewModel;
import com.example.microsftlogin.R;
import com.example.microsftlogin.UserDatabase.User;
import com.example.microsftlogin.UserDatabase.UserViewModel;
import com.example.microsftlogin.UserDatabaseRelation.UserWithAbout;
import com.example.microsftlogin.Utils.SharedPrefrenceUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    private ProgressBar aboutUserProgress;
    private Boolean connected = false;

    public AboutUserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_about_user, container, false);
        aboutuserName = view.findViewById(R.id.aboutuser_name);
        aboutuserEmail = view.findViewById(R.id.aboutuser_email);
        aboutuserPhone = view.findViewById(R.id.aboutuser_phone);
        aboutuserAddress = view.findViewById(R.id.aboutuser_address);
        aboutuserEducation = view.findViewById(R.id.aboutuser_degree);
        aboutuserDescription = view.findViewById(R.id.aboutuser_description);
        addAboutUser_btn = view.findViewById(R.id.aboutuser_submit);
        aboutUserProgress = view.findViewById(R.id.aboutuser_progress);


        addAboutUser_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                    //we are connected to a network
                    connected = true;
                } else {
                    connected = false;
                }

                if (connected == true) {
                    addAboutUser_btn.setVisibility(View.GONE);
                    aboutUserProgress.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.main_background),
                            android.graphics.PorterDuff.Mode.SRC_ATOP);
                    aboutUserProgress.setVisibility(View.VISIBLE);
                    String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    List<UserWithAbout> usersAboutInfoWithId = new ArrayList<>();
                    usersAboutInfoWithId = userViewModel.findUserWithAbout(user_id);

                    //Fields
                    String name = aboutuserName.getEditText().getText().toString();
                    String email = aboutuserEmail.getEditText().getText().toString();
                    String phone = aboutuserPhone.getEditText().getText().toString();
                    String address = aboutuserAddress.getEditText().getText().toString();
                    String education = aboutuserEducation.getEditText().getText().toString();
                    String description = aboutuserDescription.getEditText().getText().toString();

                    //Firebase
                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                    DatabaseReference aboutuserRef = FirebaseDatabase.getInstance().getReference().child("users")
                            .child(currentUser.getUid()).child("about");

                    if (!name.equals("") && !email.equals("")) {
                        AboutUser aboutUserToUpdate = usersAboutInfoWithId.get(0).getAboutUser();
                        final AboutUser newAboutUserInfo = new AboutUser(aboutUserToUpdate.getId(), name, email, phone, address, education, description, user_id);
                        aboutuserRef.child(aboutUserToUpdate.getId()).setValue(newAboutUserInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    aboutUserViewModel.update(newAboutUserInfo);
                                    Toast.makeText(getActivity(), "Information Has Been Updated", Toast.LENGTH_LONG).show();
                                    Navigation.findNavController(view).navigateUp();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(), "Something Went Wrong Please Check Your Internet Connection", Toast.LENGTH_LONG).show();
                            }
                        });
                    } else {
                        Toast.makeText(getActivity(), "Name & Email Need To Be Filled", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        userViewModel = ViewModelProviders.of(getActivity()).get(UserViewModel.class);
        aboutUserViewModel = ViewModelProviders.of(getActivity()).get(AboutUserViewModel.class);
        /*aboutUserViewModel.getAllAboutUsers().observe(getActivity(), new Observer<List<AboutUser>>() {
            @Override
            public void onChanged(List<AboutUser> aboutUsers) {

            }
        });*/

        String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();

        if (userViewModel.findUserWithAbout(user_id).size() > 0) {
            AboutUser aboutUserFound = userViewModel.findUserWithAbout(user_id).get(0).getAboutUser();
            aboutuserName.getEditText().setText(aboutUserFound.getName());
            aboutuserEmail.getEditText().setText(aboutUserFound.getEmail());
            aboutuserPhone.getEditText().setText(aboutUserFound.getPhone());
            aboutuserAddress.getEditText().setText(aboutUserFound.getAddress());
            aboutuserEducation.getEditText().setText(aboutUserFound.getEducationDegree());
            aboutuserDescription.getEditText().setText(aboutUserFound.getDescription());
        }

    }

    public void onResume() {
        super.onResume();

       userViewModel = ViewModelProviders.of(getActivity()).get(UserViewModel.class);
       aboutUserViewModel = ViewModelProviders.of(getActivity()).get(AboutUserViewModel.class);
       aboutUserViewModel.getAllAboutUsers().observe(getActivity(), new Observer<List<AboutUser>>() {
           @Override
           public void onChanged(List<AboutUser> aboutUsers) {

           }
       });

       String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();

       if (userViewModel.findUserWithAbout(user_id).size() > 0) {
           AboutUser aboutUserFound = userViewModel.findUserWithAbout(user_id).get(0).getAboutUser();
           aboutuserName.getEditText().setText(aboutUserFound.getName());
           aboutuserEmail.getEditText().setText(aboutUserFound.getEmail());
           aboutuserPhone.getEditText().setText(aboutUserFound.getPhone());
           aboutuserAddress.getEditText().setText(aboutUserFound.getAddress());
           aboutuserEducation.getEditText().setText(aboutUserFound.getEducationDegree());
           aboutuserDescription.getEditText().setText(aboutUserFound.getDescription());
       }


      /* if (userViewModel.findUserWithAbout(user_id).get(0).getAboutUserList().size() > 0) {
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
       } */
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
