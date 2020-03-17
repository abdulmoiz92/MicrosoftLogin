package com.example.microsftlogin.UserAchievementsDatabase;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.microsftlogin.UserDatabase.UserRoomDatabase;

import java.util.List;

public class UserAchievementViewModel extends AndroidViewModel {
    private UserAchievementRepository userAchievementRepository;
    private LiveData<List<UserAchievement>> allUserAchievements;

    public UserAchievementViewModel(@NonNull Application application) {
        super(application);
        userAchievementRepository = new UserAchievementRepository(application);
        allUserAchievements = userAchievementRepository.getAllUserAchievements();
    }

    public LiveData<List<UserAchievement>> getAllUserAchievements() { return allUserAchievements; }

    public void insert(UserAchievement userAchievement) { userAchievementRepository.insert(userAchievement); }

    public void delete(UserAchievement userAchievement) { userAchievementRepository.delete(userAchievement); }
}
