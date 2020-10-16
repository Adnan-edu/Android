package com.example.movieapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Cinema implements Serializable {
    @SerializedName("cinemaid")
    private Integer cinemaid;
    @SerializedName("cinemaname")
    private String cinemaname;
    @SerializedName("suburb")
    private String suburb;
    @SerializedName("postcode")
    private String postcode;

    public Cinema(){

    }
    public Cinema(Integer cinemaid, String cinemaname, String suburb, String postcode) {
        this.cinemaname = cinemaname;
        this.suburb = suburb;
        this.postcode = postcode;
        this.cinemaid = cinemaid;
    }
    public Cinema(String cinemaname, String suburb, String postcode) {
        this.cinemaname = cinemaname;
        this.suburb = suburb;
        this.postcode = postcode;
    }

    public String getCinemaname() {
        return cinemaname;
    }

    public void setCinemaname(String cinemaname) {
        this.cinemaname = cinemaname;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public Integer getCinemaid() {
        return cinemaid;
    }

    public void setCinemaid(Integer cinemaid) {
        this.cinemaid = cinemaid;
    }
}
