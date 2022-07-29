package com.example.b07_project;

import com.example.b07_project.Address;
import com.example.b07_project.Time;

import java.util.ArrayList;
import java.util.Objects;

public class Venue {
    public int id;
    public String name;
    public String description;
    public Address address;
    public ArrayList<String> sports;
    public int maxConcurrentActivities;
    public int capacity;

    public ArrayList<String> getSports() {
        return sports;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        com.example.b07_project.Venue venue = (com.example.b07_project.Venue) o;
        return id == venue.id && capacity == venue.capacity && Objects.equals(name, venue.name) && Objects.equals(description, venue.description) && Objects.equals(sports, venue.sports) && Objects.equals(scheduledEvents, venue.scheduledEvents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
