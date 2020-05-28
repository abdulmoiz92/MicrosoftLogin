package com.example.microsftlogin.UserSkillsDatabase;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class UserSkillViewModel extends AndroidViewModel {
    private UserSkillRepository userSkillRepository;
    private LiveData<List<UserSkill>> allUserSkills;

    public UserSkillViewModel(@NonNull Application application) {
        super(application);
        userSkillRepository = new UserSkillRepository(application);
        allUserSkills = userSkillRepository.getAllUserSkills();
    }

    public LiveData<List<UserSkill>> getAllUserSkills() { return allUserSkills; }

    public void insert(UserSkill userSkill) { userSkillRepository.insert(userSkill); }

    public void delete(UserSkill userSkill) { userSkillRepository.delete(userSkill); }

    public void deleteAllUserSkill() { userSkillRepository.deleteAllUserSkill(); }
}
