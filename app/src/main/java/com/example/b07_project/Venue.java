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



    public Venue(){

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Venue venue = (Venue) o;
        return id == venue.id && capacity == venue.capacity && Objects.equals(name, venue.name) && Objects.equals(description, venue.description) && Objects.equals(sports, venue.sports) && Objects.equals(scheduledEvents, venue.scheduledEvents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
