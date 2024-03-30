package com.example.finalproject.model;

import androidx.room.Room;

import com.example.finalproject.FinalProjectApplication;
import com.example.finalproject.repositories.AppLocalDbRepository;

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

