package com.example.b07_project;

import java.util.ArrayList;

public class User {
    public String id;
    public String firstName, lastName;
    public String email;
    public ArrayList<Integer> joinedEvents; //list of event IDs
    public ArrayList<Integer> createdEvents;
    public int auth; //0 -> customer, 1 -> admin

    public User()
    {
        joinedEvents = new ArrayList<Integer>();
        createdEvents = new ArrayList<Integer>();
    }

}
