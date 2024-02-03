package com.example.finalproject.model;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.finalproject.FinalProjectApplication;

@Database(entities = {Review.class}, version = 2)
abstract class AppLocalDbRepository extends RoomDatabase {
    public abstract ReviewDao reviewDao();
}

public class AppLocalDb{
    static public AppLocalDbRepository getAppDb() {
        return Room.databaseBuilder(FinalProjectApplication.getMyContext(),
                        AppLocalDbRepository.class,
                        "dbFileName.db")
                .fallbackToDestructiveMigration()
                .build();
    }

    private AppLocalDb(){}
}

