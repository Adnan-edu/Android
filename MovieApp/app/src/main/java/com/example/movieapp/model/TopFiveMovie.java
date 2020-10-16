package com.example.movieapp.model;

import java.io.Serializable;

public class TopFiveMovie implements Serializable {
    private String moviename;
    private String moviereldate;
    private Double ratingscore;
    public TopFiveMovie(){}

    public TopFiveMovie(String moviename, String moviereldate, Double ratingscore) {
        this.moviename = moviename;
        this.moviereldate = moviereldate;
        this.ratingscore = ratingscore;
    }

    public String getMoviename() {
        return moviename;
    }

    public void setMoviename(String moviename) {
        this.moviename = moviename;
    }

    public String getMoviereldate() {
        return moviereldate;
    }

    public void setMoviereldate(String moviereldate) {
        this.moviereldate = moviereldate;
    }

    public Double getRatingscore() {
        return ratingscore;
    }

    public void setRatingscore(Double ratingscore) {
        this.ratingscore = ratingscore;
    }
}
