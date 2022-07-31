package com.example.b07_project;

import java.util.ArrayList;

public class User {
    public String id;
    public String firstName, lastName;
    public String email;
    public ArrayList<Integer> events; //list of event IDs
    public int auth; //0 -> customer, 1 -> admin

    public User()
    {
        events = new ArrayList<Integer>();
    }

}
