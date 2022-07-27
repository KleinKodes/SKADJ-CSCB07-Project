package com.example.b07_project;

import android.location.Address;

import java.util.List;

public class Venue {
    int id;
    String name;
    String description;
    Address address;
    List<String> sports;
    int capacity;

    public Venue(){

    }
    
    public boolean verifyCapacity(int numSpots){ // numSpots refers to the number of people in the event
        if(numSpots / this.capacity == 1){return false;}
        else{return true;}
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
