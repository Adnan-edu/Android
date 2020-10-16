package com.example.movieapp.model;

public class MovieMem {
    private Memoir memoir;
    private ImageTitle imageTitle;
    private String year;
    public MovieMem(){

    }

    public MovieMem(Memoir memoir, ImageTitle imageTitle) {
        this.memoir = memoir;
        this.imageTitle = imageTitle;
    }

    public Memoir getMemoir() {
        return memoir;
    }

    public void setMemoir(Memoir memoir) {
        this.memoir = memoir;
    }

    public ImageTitle getImageTitle() {
        return imageTitle;
    }

    public void setImageTitle(ImageTitle imageTitle) {
        this.imageTitle = imageTitle;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
