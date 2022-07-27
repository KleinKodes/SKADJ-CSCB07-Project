package com.example.b07_project;

import java.util.Date;

public class Event {
    int id;
    String name;
    String sport;
    Date startTime, endTime;
    int attendeeNum;
    String eventDescription;
    int ownerId;
    int venueId;


    public Event()
    {

    }
    public Event(String name)
    {
        this.name = name;
        this.id = 0;
    }

    
    @Override
    public int hashCode(){
        return id;
    }

    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof Event)){return false;}
        Event newObj = (Event)obj;
        if(newObj.id == this.id){return true;}
        else{return false;}
    }
}
