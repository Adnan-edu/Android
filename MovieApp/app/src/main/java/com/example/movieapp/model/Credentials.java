package com.example.movieapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Credentials implements Serializable {
    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;
    @SerializedName("signupdate")
    private String signupdate;
    @SerializedName("personid")
    private Person personid;
    public Credentials(){}

    public Credentials(String username, String password, String signupdate, Person personid) {
        this.username = username;
        this.password = password;
        this.signupdate = signupdate;
        this.personid = personid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSignupdate() {
        return signupdate;
    }

    public void setSignupdate(String signupdate) {
        this.signupdate = signupdate;
    }

    public Person getPersonid() {
        return personid;
    }

    public void setPersonid(Person personid) {
        this.personid = personid;
    }
}
