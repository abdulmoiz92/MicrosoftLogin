package com.example.microsftlogin.UserDatabase;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class UserViewModel extends AndroidViewModel {
    private UserRepository userRepository;
    private LiveData<List<User>> uAllUsers;

    public UserViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
        uAllUsers = userRepository.getAllUsers();
    }




    public void insert(User user) { userRepository.insert(user); }

    public LiveData<List<User>> findUser(String email) {  return userRepository.findUser(email); }

    public LiveData<List<User>> getuAllUsers() { return uAllUsers; }

   // public LiveData<List<User>> getSearchResults() { return userRepository.getSearchResults(); }

}
