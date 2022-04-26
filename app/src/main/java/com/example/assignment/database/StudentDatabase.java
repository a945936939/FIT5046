package com.example.assignment.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.assignment.dao.StudentDao;
import com.example.assignment.entity.Student;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Student.class}, version = 1, exportSchema = false)
public abstract class StudentDatabase extends RoomDatabase {

    public abstract StudentDao studentDao();

    private static volatile StudentDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static StudentDatabase getInstance(final Context context){
        if (INSTANCE == null) {
            synchronized (StudentDatabase.class){
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            StudentDatabase.class, "student_database")
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
                StudentDao dao = INSTANCE.studentDao();
                dao.deleteAll();

                Student student = new Student("001", "Frank","Zhang");
                dao.insert(student);
                student = new Student("002", "Jerry", "Yang");
                dao.insert(student);
                student = new Student("003", "Daniel", "Wu");
                dao.insert(student);

            });
        }
    };
}
