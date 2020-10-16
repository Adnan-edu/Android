package com.example.movieapp.model;

import java.io.Serializable;

public class MoviesPerMonth implements Serializable {
    private String month;
    private int count;

    public MoviesPerMonth(){}

    public MoviesPerMonth(String month, int count) {
        this.month = month;
        this.count = count;
    }
    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
