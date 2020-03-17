package com.example.microsftlogin.UserProjectsDatabase;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class UserProjectViewModel extends AndroidViewModel {
    private UserProjectRepository userProjectRepository;
    private LiveData<List<UserProject>> allUserProjects;

    public UserProjectViewModel(@NonNull Application application) {
        super(application);
        userProjectRepository = new UserProjectRepository(application);
        allUserProjects = userProjectRepository.getAllUserProjects();
    }

    public LiveData<List<UserProject>> getAllUserProjects() { return allUserProjects; }

    public void insert(UserProject userProject) { userProjectRepository.insert(userProject); }

    public void update(UserProject userProject) { userProjectRepository.update(userProject); }

    public void delete(UserProject userProject) { userProjectRepository.delete(userProject); }
}
