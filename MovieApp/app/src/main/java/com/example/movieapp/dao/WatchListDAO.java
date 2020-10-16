package com.example.movieapp.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.movieapp.entity.WatchList;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface WatchListDAO {
    @Query("SELECT * FROM watchlist")
    LiveData<List<WatchList>> getAll();
    @Query("SELECT * FROM watchlist WHERE uid = :watchlistId LIMIT 1")
    WatchList findByID(int watchlistId);

    @Insert
    void insertAll(WatchList... watchLists);

    @Insert
    long insert(WatchList watchList);

    @Delete
    void delete(WatchList watchList);
    
    @Update(onConflict = REPLACE)
    void updateWatchList(WatchList... watchlists);
    @Query("DELETE FROM watchlist") void deleteAll();

    @Query("UPDATE watchlist SET movieNameRelYear=:movieNameRelYear, storageDate=:storageDate WHERE uid = :id")
    void updatebyID(int id, String movieNameRelYear, String storageDate);

}
