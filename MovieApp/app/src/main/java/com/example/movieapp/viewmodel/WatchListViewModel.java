package com.example.movieapp.viewmodel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movieapp.entity.WatchList;
import com.example.movieapp.repository.WatchListRepository;

import java.util.List;

public class WatchListViewModel  extends ViewModel {
    private WatchListRepository cRepository;
    private MutableLiveData<List<WatchList>> allWatchLists;
    public WatchListViewModel ()
    {
        allWatchLists=new MutableLiveData<>();
    }
    public void setWatchLists(List<WatchList> watchLists)
    {
        allWatchLists.setValue(watchLists);
    }
    public LiveData<List<WatchList>> getAllWatchLists()
    {
        return cRepository.getAllWatchList();
    }
    public void initalizeVars(Application application)
    {
        cRepository = new WatchListRepository(application);
    }
    public void insert(WatchList watchList)
    {
        cRepository.insert(watchList);
    }
    public void insertAll(WatchList... watchLists)
    {
        cRepository.insertAll(watchLists);
    }
    public void deleteAll()
    {
        cRepository.deleteAll();
    }
    public void delete(WatchList watchList){cRepository.delete(watchList);}
    public void update(WatchList... watchLists)
    {
        cRepository.updateWatchLists(watchLists);
    }
    public void updateByID(int watchListId, String movieNameRelYear, String storageDate)
    {
        cRepository.updateWatchListByID(watchListId,movieNameRelYear, storageDate);
    }
    public WatchList findByID(int watchListId){
        return cRepository.findByID(watchListId);
    }

}
