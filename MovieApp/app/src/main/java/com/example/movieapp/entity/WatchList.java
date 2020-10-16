package com.example.movieapp.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity
public class WatchList implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int uid;
    @ColumnInfo(name = "movieNameRelYear")
    public String movieNameRelYear;
    @ColumnInfo(name = "storageDate")
    public String storageDate;
    public WatchList(){

    }
    public WatchList(String movieNameRelYear, String storageDate) {
        this.movieNameRelYear = movieNameRelYear;
        this.storageDate = storageDate;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getMovieNameRelYear() {
        return movieNameRelYear;
    }

    public void setMovieNameRelYear(String movieNameRelYear) {
        this.movieNameRelYear = movieNameRelYear;
    }

    public String getStorageDate() {
        return storageDate;
    }

    public void setStorageDate(String storageDate) {
        this.storageDate = storageDate;
    }
}
