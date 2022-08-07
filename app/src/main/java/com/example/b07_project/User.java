package com.example.b07_project;

import java.util.ArrayList;

public class User {
    public String id;
    public String firstName, lastName;
    public String email;
    public ArrayList<Integer> joinedEvents; 
    public int auth; 
    public ArrayList<Integer> createdEvents;

    public User()
    {
        joinedEvents = new ArrayList<Integer>();
        createdEvents = new ArrayList<Integer>();
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName(){return getFirstName() + " " + getLastName();}

    public String getEmail() {
        return email;
    }

    public ArrayList<Integer> getJoinedEvents() {
        return joinedEvents;
    }

    public int getAuth() {
        return auth;
    }

    public ArrayList<Integer> getCreatedEvents() {
        return createdEvents;
    }

    @Override
    public String toString() {
        return "User: " + getFullName() + "\nEmail: " + getEmail() + "\nAuth: " + getAuth() + "\nId: " + getId();
    }
}
