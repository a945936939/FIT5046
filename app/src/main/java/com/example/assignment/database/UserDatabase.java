package com.example.assignment.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.assignment.dao.UserDao;
import com.example.assignment.entity.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {

    public abstract UserDao userDao();

    private static volatile UserDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static UserDatabase getInstance(final Context context){
        if (INSTANCE == null) {
            synchronized (UserDatabase.class){
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            UserDatabase.class, "user_database")
                            .addCallback(sRoomDatabaseCallback)
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                UserDao dao = INSTANCE.userDao();
                dao.deleteAll();

                User user = new User("001", "Frank","Zhang");
                dao.insert(user);
                user = new User("002", "Jerry", "Yang");
                dao.insert(user);
                user = new User("003", "Daniel", "Wu");
                dao.insert(user);

            });
        }
    };
}
