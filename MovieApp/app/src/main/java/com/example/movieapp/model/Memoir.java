package com.example.movieapp.model;

import java.io.Serializable;

public class Memoir implements Serializable {
    private Cinema cinemaid;
    private String comment;
    private Integer memoirid;
    private String moviename;
    private String moviereldate;
    private Person personid;
    private Double ratingscore;
    private String watchingdatetime;

    public Memoir(){

    }

    public Memoir(Cinema cinemaid, String comment, Integer memoirid, String moviename, String moviereldate, Person personid, Double ratingscore, String watchingdatetime) {
        this.cinemaid = cinemaid;
        this.comment = comment;
        this.memoirid = memoirid;
        this.moviename = moviename;
        this.moviereldate = moviereldate;
        this.personid = personid;
        this.ratingscore = ratingscore;
        this.watchingdatetime = watchingdatetime;
    }

    public Cinema getCinemaid() {
        return cinemaid;
    }

    public void setCinemaid(Cinema cinemaid) {
        this.cinemaid = cinemaid;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getMemoirid() {
        return memoirid;
    }

    public void setMemoirid(Integer memoirid) {
        this.memoirid = memoirid;
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

    public Person getPersonid() {
        return personid;
    }

    public void setPersonid(Person personid) {
        this.personid = personid;
    }

    public Double getRatingscore() {
        return ratingscore;
    }

    public void setRatingscore(Double ratingscore) {
        this.ratingscore = ratingscore;
    }

    public String getWatchingdatetime() {
        return watchingdatetime;
    }

    public void setWatchingdatetime(String watchingdatetime) {
        this.watchingdatetime = watchingdatetime;
    }
}
