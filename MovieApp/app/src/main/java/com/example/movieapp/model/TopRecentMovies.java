package com.example.movieapp.model;

public class TopRecentMovies {
    private String moviename;
    private String moviereldate;
    private float ratingscore;

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

    public float getRatingscore() {
        return ratingscore;
    }

    public void setRatingscore(float ratingscore) {
        this.ratingscore = ratingscore;
    }
}
