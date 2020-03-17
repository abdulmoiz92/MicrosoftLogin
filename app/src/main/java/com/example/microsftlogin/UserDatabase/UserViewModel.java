package com.example.microsftlogin.UserDatabase;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.microsftlogin.UserDatabaseRelation.UserWithAbout;
import com.example.microsftlogin.UserDatabaseRelation.UserWithAchievement;
import com.example.microsftlogin.UserDatabaseRelation.UserWithEducation;
import com.example.microsftlogin.UserDatabaseRelation.UserWithExperience;
import com.example.microsftlogin.UserDatabaseRelation.UserWithProject;
import com.example.microsftlogin.UserDatabaseRelation.UserWithSkill;
import com.example.microsftlogin.UserEducationDatabase.UserEducation;

import java.util.List;

public class UserViewModel extends AndroidViewModel {
    private UserRepository userRepository;
    private LiveData<List<User>> uAllUsers;
    private LiveData<List<UserWithAbout>> allUsersWithAbout;
    private LiveData<List<UserWithExperience>> allUsersWithExperience;
    private LiveData<List<UserWithEducation>> allUserWithEducation;
    private LiveData<List<UserWithSkill>> allUserWithSkill;
    private LiveData<List<UserWithProject>> allUsersWithProjets;
    private LiveData<List<UserWithAchievement>> allUsersWithAchievements;

    public UserViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
        uAllUsers = userRepository.getAllUsers();
        allUsersWithAbout = userRepository.getAllUsersWithAbout();
        allUsersWithExperience = userRepository.getAllUsersWithExperience();
        allUserWithEducation = userRepository.getAllUserWithEducation();
        allUserWithSkill = userRepository.getAllUserWithSkill();
        allUsersWithProjets = userRepository.getAllUsersWithProjects();
        allUsersWithAchievements = userRepository.getAllUsersWithAchievements();
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

    //Users Education Information

    public LiveData<List<UserWithEducation>> getAllUserWithEducation() { return allUserWithEducation; }

    public List<UserWithEducation> findUserWithEducation(int id) { return userRepository.findUserWithEducation(id); }

    //Users Skills Information

    public LiveData<List<UserWithSkill>> getAllUserWithSkill() { return allUserWithSkill; }

    public List<UserWithSkill> findUserWithSkill(int id) { return userRepository.findUserWithSkill(id); }

    //Users Projects Information


    public LiveData<List<UserWithProject>> getAllUsersWithProjets() { return allUsersWithProjets; }

    public List<UserWithProject> findUserWithProject(int id) { return userRepository.findUserWithProject(id); }

    //Users Achievements Information

    public LiveData<List<UserWithAchievement>> getAllUsersWithAchievement() { return allUsersWithAchievements; }
    public List<UserWithAchievement> findUserWithAchievement(int id) { return userRepository.findUserWithAchievement(id); }
}
