package com.example.calorietracker;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {StepsTable.class}, version = 5, exportSchema = false)
public abstract class StepsDatabase extends RoomDatabase {
    public abstract StepsDAO stepsDAO();
    private static volatile StepsDatabase INSTANCE;
    static StepsDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (StepsDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE =
                            Room.databaseBuilder(context.getApplicationContext(),
                                    StepsDatabase.class, "step_database")
                                    .build();
                }
            }
        }
        return INSTANCE;
    }
}
