package com.example.microsftlogin.UserDatabase;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.microsftlogin.UserDatabaseRelation.UserWithAbout;
import com.example.microsftlogin.UserDatabaseRelation.UserWithExperience;

import java.util.List;

public class UserViewModel extends AndroidViewModel {
    private UserRepository userRepository;
    private LiveData<List<User>> uAllUsers;
    private LiveData<List<UserWithAbout>> allUsersWithAbout;
    private LiveData<List<UserWithExperience>> allUsersWithExperience;

    public UserViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
        uAllUsers = userRepository.getAllUsers();
        allUsersWithAbout = userRepository.getAllUsersWithAbout();
        allUsersWithExperience = userRepository.getAllUsersWithExperience();

    }



    // User Database Functions

    public void insert(User user) { userRepository.insert(user); }

    public List<User> findUser(String email) {  return userRepository.findUser(email); }

    public List<User> findUser(int id) { return userRepository.findUser(id); }

    public LiveData<List<User>> getuAllUsers() { return uAllUsers; }


    // Users About Information

    public LiveData<List<UserWithAbout>> getAllUsersWithAbout() { return allUsersWithAbout; }

    public List<UserWithAbout> findUserWithAbout(int id) { return userRepository.findUserWithAbout(id); }


    // Users Experience Information

    public LiveData<List<UserWithExperience>> getAllUsersWithExperience() { return allUsersWithExperience; }

    public List<UserWithExperience> findUserWithExperiences(int id) { return userRepository.findUserWithExperience(id); }

}
