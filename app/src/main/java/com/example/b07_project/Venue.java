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

    public Venue(int id, String name, String description, Address address, ArrayList<String> sports, int capacity){
        this.name = name;
        this.id = id;
        this.description = description;
        this.address = address;
        this.sports = sports;
        this.capacity = capacity;
    }
    
    public boolean verifyCapacity(int numSpots){ // numSpots refers to the number of people in the event
        if(numSpots / this.capacity == 1){return false;}
        else{return true;}
    }

    public String getName(){
        return this.name;
    }

    public String getCapactiy(){
        return "Capacity: " + this.capacity;
    }

    public static ArrayList<Venue> createVenueList(){
        return null;
    }


    @Override
    public int hashCode(){
        return id;
    }

    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof Venue)){return false;}
        Venue newObj = (Venue)obj;
        if(newObj.id == this.id){return true;}
        else{return false;}
    }
}
