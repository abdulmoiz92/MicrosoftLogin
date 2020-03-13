package com.example.microsftlogin.UserDatabase;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.microsftlogin.AboutUserDatabase.AboutUser;
import com.example.microsftlogin.AboutUserDatabase.AboutUserDao;
import com.example.microsftlogin.UserExperienceDatabase.UserExperience;
import com.example.microsftlogin.UserExperienceDatabase.UserExperienceDao;

@Database(entities = {User.class,AboutUser.class,UserExperience.class}, version = 5, exportSchema = false)
public abstract class UserRoomDatabase extends RoomDatabase {

    public abstract UserDao userDao();
    public abstract AboutUserDao aboutUserDao();
    public abstract UserExperienceDao userExperienceDao();

    public static UserRoomDatabase INSTANCE;

    public static UserRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (UserRoomDatabase.class) {
                if (INSTANCE == null) {
                    //Create Database Here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            UserRoomDatabase.class,"user_database")

                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .addCallback(uRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }



    private static UserRoomDatabase.Callback uRoomDatabaseCallback =
       new RoomDatabase.Callback() {
           @Override
           public void onOpen(@NonNull SupportSQLiteDatabase db) {
               super.onOpen(db);
               new PopulateDbAsync(INSTANCE).execute();
           }
       };

    /**
     * Populate the database in the background.
     */
    private static class PopulateDbAsync extends AsyncTask<Void,Void,Void> {
        private final UserDao dao;
        private final AboutUserDao aboutUserDao;
        private final UserExperienceDao userExperienceDao;

        PopulateDbAsync(UserRoomDatabase db) {
            this.dao = db.userDao();
            this.aboutUserDao = db.aboutUserDao();
            this.userExperienceDao = db.userExperienceDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            // when it is first created
            dao.getAllUser();
            aboutUserDao.getAllAboutUser();
            userExperienceDao.getAllUserExperience();


            return null;
        }
    }

}
