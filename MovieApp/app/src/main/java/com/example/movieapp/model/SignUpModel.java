package com.example.movieapp.model;

public class SignUpModel {
    private Person person;
    private Credentials credentials;
    public SignUpModel(){}

    public SignUpModel(Person person, Credentials credentials) {
        this.person = person;
        this.credentials = credentials;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }
}
