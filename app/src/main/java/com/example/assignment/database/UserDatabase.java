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

                User user = new User("001", "Frank","Zhang","Male", 26,"1996", 187.2);
                dao.insert(user);
                user = new User("002", "Jerry", "Yang", "Male", 24,"1998", 178.3);
                dao.insert(user);
                user = new User("003", "Daniel", "Wu","Male",24,"1998", 185);
                dao.insert(user);
                user = new User("004", "Sam", "Wang","Male",25,"1997", 180);
                dao.insert(user);
                user = new User("005", "Leo", "Zhang","Male",24,"1998", 180);
                dao.insert(user);
                user = new User("006", "Angela", "Fu","Female",25,"1997", 156);
                dao.insert(user);
                user = new User("007", "Marvin", "Liu","Male",24,"1998", 175);
                dao.insert(user);
                user = new User("008", "Anna", "Tanikawa","Female",25,"1997", 155);
                dao.insert(user);
                user = new User("009", "Chini", "Xiao","Female",19,"2003", 163);
                dao.insert(user);
                user = new User("010", "Jane", "Chen","Female",25,"1997", 165);
                dao.insert(user);

            });
        }
    };
}
