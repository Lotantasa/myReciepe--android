package com.example.finalproject.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Dao
public interface ReviewDao {
    @Query("select * from Review")
    LiveData<List<Review>> getAll();

    @Query("select * from Review where userId =:userId")
    LiveData<List<Review>> getUserReviews(String userId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Review... reviews);

}




