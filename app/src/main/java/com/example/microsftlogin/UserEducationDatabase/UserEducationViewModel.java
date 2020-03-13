package com.example.microsftlogin.UserEducationDatabase;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class UserEducationViewModel extends AndroidViewModel {
    private UserEducationRepository userEducationRepository;
    private LiveData<List<UserEducation>> allUserEducations;

    public UserEducationViewModel(@NonNull Application application) {
        super(application);
        userEducationRepository = new UserEducationRepository(application);
        allUserEducations = userEducationRepository.getAllUserEducations();
    }

    public LiveData<List<UserEducation>> getAllUserEducations() { return allUserEducations; }

    public void insert(UserEducation userEducation) { userEducationRepository.insert(userEducation); }

}
