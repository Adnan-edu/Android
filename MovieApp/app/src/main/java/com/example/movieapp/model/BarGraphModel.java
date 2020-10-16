package com.example.movieapp.model;

import java.io.Serializable;

public class BarGraphModel implements Serializable {
    private Integer personid;
    private String suburb;
    private Integer count;
    public BarGraphModel(){}

    public BarGraphModel(Integer personid, String suburb, Integer count) {
        this.personid = personid;
        this.suburb = suburb;
        this.count = count;
    }

    public Integer getPersonid() {
        return personid;
    }

    public void setPersonid(Integer personid) {
        this.personid = personid;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
