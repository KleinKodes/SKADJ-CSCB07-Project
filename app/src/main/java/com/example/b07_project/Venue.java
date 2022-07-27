package com.example.b07_project;

import android.location.Address;

import java.util.ArrayList;
import java.util.List;

public class Venue {
    int id;
    String name;
    String description;
    Address address;
    ArrayList<String> sports;
    int capacity;
    ArrayList<Integer> scheduledEvents;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Address getAddress() {
        return address;
    }

    public ArrayList<String> getSports() {
        return sports;
    }

    public int getCapacity() {
        return capacity;
    }

    public ArrayList<Integer> getScheduledEvents() {
        return scheduledEvents;
    }



    public Venue(){

    }
}
