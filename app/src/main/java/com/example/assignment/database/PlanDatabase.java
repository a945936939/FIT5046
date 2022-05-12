package com.example.assignment.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.assignment.dao.PlanDao;
import com.example.assignment.dao.PlanDao;
import com.example.assignment.entity.Plan;
import com.example.assignment.entity.Plan;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Plan.class}, version = 1, exportSchema = false)

public abstract class PlanDatabase extends RoomDatabase {
    public abstract PlanDao planDao();
    private static PlanDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static synchronized PlanDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    PlanDatabase.class, "PlanDatabase")
                    .addCallback(sRoomDatabaseCallback)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
    
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                PlanDao dao = INSTANCE.planDao();
                dao.deleteAll();

                Plan plan = new Plan("Running", "01/05/2022","Running for one hour");
                dao.insert(plan);
                plan = new Plan("Swimming", "01/06/2022", "Swimming for 30 minutes");
                dao.insert(plan);

            });
        }
    };
}

