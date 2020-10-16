package com.example.movieapp.appcontext;

import android.app.Application;
import com.example.movieapp.model.Person;

public class MovieGlobal extends Application {
    private Person person;
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
