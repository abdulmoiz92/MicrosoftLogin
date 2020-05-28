package com.example.microsftlogin.UserExperienceDatabase;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class UserExperienceViewModel extends AndroidViewModel {
    private UserExperienceRepository userExperienceRepository;
    private LiveData<List<UserExperience>> allUsersExperience;

    public UserExperienceViewModel(@NonNull Application application) {
        super(application);
        userExperienceRepository = new UserExperienceRepository(application);
        allUsersExperience = userExperienceRepository.getAllUserExperience();
    }

    public LiveData<List<UserExperience>> getAllUsersExperience() { return allUsersExperience; }

    public void insert(UserExperience userExperience) { userExperienceRepository.insert(userExperience); }

    public void update(UserExperience userExperience) { userExperienceRepository.update(userExperience); }

    public void delete(UserExperience userExperience) { userExperienceRepository.delete(userExperience); }

    public void deleteAllUserExperience() { userExperienceRepository.deleteAllUserExperience(); }
}
