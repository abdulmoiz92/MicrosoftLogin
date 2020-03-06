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

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class UserRoomDatabase extends RoomDatabase {

    public abstract UserDao userDao();

    public static UserRoomDatabase INSTANCE;

    public static UserRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (UserRoomDatabase.class) {
                if (INSTANCE == null) {
                    //Create Database Here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            UserRoomDatabase.class,"user_database")

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

        PopulateDbAsync(UserRoomDatabase db) { this.dao = db.userDao(); }

        @Override
        protected Void doInBackground(Void... voids) {
            // when it is first created
            dao.getAllUser();


            return null;
        }
    }

}
