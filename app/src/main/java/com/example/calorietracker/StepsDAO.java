package com.example.calorietracker;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.sql.Time;
import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface StepsDAO {
    @Query("SELECT * FROM stepsTable")
    List<StepsTable> getAll();
    @Query("SELECT * FROM stepstable WHERE time LIKE :time AND " +
            "steps_Taken LIKE :steps LIMIT 1")
    StepsTable findByTimeAndSteps(String time, Integer steps);
    @Query("SELECT * FROM stepsTable WHERE sid = :stepsTableId LIMIT 1")
    StepsTable findByID(int stepsTableId);
    @Query("SELECT SUM(steps_Taken) FROM stepsTable")
    Integer findStepsTaken();
    @Insert
    void insertAll(StepsTable... stepsTables);
    @Insert
    long insert(StepsTable stepsTable);
    @Delete
    void delete(StepsTable stepsTable);
    @Update(onConflict = REPLACE)
    public void updateUsers(StepsTable... stepsTables);
    @Query("DELETE FROM stepsTable")
    void deleteAll();
}
