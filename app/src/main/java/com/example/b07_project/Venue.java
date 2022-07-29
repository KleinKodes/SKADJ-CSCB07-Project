package com.example.b07_project;

import android.location.Address;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Venue {
    int id;
    String name;
    String description;
    //Address address;
    ArrayList<String> sports;
    int capacity;
    ArrayList<Integer> scheduledEvents;

    public Venue(int id, String name, String description, ArrayList<String> sports, int capacity){
        this.name = name;
        this.id = id;
        this.description = description;
        //this.address = address;
        this.sports = sports;
        this.capacity = capacity;
    }
    
    public boolean verifyCapacity(int numSpots){ // numSpots refers to the number of people in the event
        if(numSpots / this.capacity == 1){return false;}
        else{return true;}
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
//
//    public Address getAddress() {
//        return address;
//    }

    public ArrayList<String> getSports() {
        return sports;
    }

    public int getCapacity() {
        return capacity;
    }

    public ArrayList<Integer> getScheduledEvents() {
        return scheduledEvents;
    }

}
