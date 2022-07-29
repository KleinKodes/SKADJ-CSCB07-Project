package com.example.b07_project.Anthony;

import java.util.ArrayList;

public class Venue {
    public int id;
    public String name;
    public String description;
    public Address address;
    public ArrayList<String> sports;
    public int maxConcurrentActivities;
    public int capacity;
    public ArrayList<Integer> scheduledEvents;
    public Time availableFrom, availableTo;
    public String daysAvailable; //String containing 7 characters. "1" means available and "0" means its not. Week starts on Monday


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

    public ArrayList<String> getActivities() {
        return this.sports;
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
