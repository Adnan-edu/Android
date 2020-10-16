package com.example.movieapp.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.movieapp.dao.WatchListDAO;
import com.example.movieapp.database.WatchListDatabase;
import com.example.movieapp.entity.WatchList;

import java.util.List;

public class WatchListRepository {
    private WatchListDAO dao;
    private LiveData<List<WatchList>> allWatchList;
    private WatchList watchList;

    public WatchListRepository(Application application) {
        WatchListDatabase db = WatchListDatabase.getInstance(application);
        dao = db.watchlistDao();
    }

    public LiveData<List<WatchList>> getAllWatchList() {
        allWatchList = dao.getAll();
        return allWatchList;
    }

    public void insert(final WatchList watchList) {
        WatchListDatabase.databaseWriteExecutor.execute(
                new Runnable() {
                    @Override
                    public void run() {
                        dao.insert(watchList);
                    }
                });
    }

    public void deleteAll() {
        WatchListDatabase.databaseWriteExecutor.execute(
                new Runnable() {
                    @Override
                    public void run() {
                        dao.deleteAll();
                    }
                });
    }

    public void delete(final WatchList watchList) {
        WatchListDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.delete(watchList);
            }
        });
    }

    public void insertAll(final WatchList... watchLists) {
        WatchListDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.insertAll(watchLists);
            }
        });
    }

    public void updateWatchLists(final WatchList... watchLists) {
        WatchListDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.updateWatchList(watchLists);
            }
        });
    }

    public void updateWatchListByID(final int watchListId, final String movieNameRelYear, final String storageDate) {
        WatchListDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.updatebyID(watchListId, movieNameRelYear, storageDate);
            }
        });
    }
    public WatchList findByID(final int watchListId)
    {
        WatchListDatabase.databaseWriteExecutor.execute(new Runnable()
        {
            @Override public void run()
            {
                WatchList runWatchList= dao.findByID(watchListId);
                setWatchList(runWatchList);
            } });
        return watchList;
    }
    public void setWatchList(WatchList watchList)
    {
        this.watchList=watchList;
    }

}
