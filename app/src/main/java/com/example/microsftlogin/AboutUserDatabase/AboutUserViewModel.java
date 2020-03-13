package com.example.microsftlogin.AboutUserDatabase;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class AboutUserViewModel extends AndroidViewModel {
    private AboutUserRepository aboutUserRepository;
    private LiveData<List<AboutUser>> mAllAboutUsers;

    public AboutUserViewModel(@NonNull Application application) {
        super(application);
        aboutUserRepository = new AboutUserRepository(application);
        mAllAboutUsers = aboutUserRepository.getAllAboutUser();
    }

    public LiveData<List<AboutUser>> getAllAboutUsers() { return mAllAboutUsers; }

    public void insert(AboutUser aboutUser) { aboutUserRepository.insert(aboutUser); }

    public void update(AboutUser aboutUser) { aboutUserRepository.update(aboutUser); }
}
