package com.example.b07_project;

import java.util.Date;

public class Event {
    public int id;
    public String name;
    public String sport;
    public Date startTime, endTime;
    public int numOfParticipants;
    public String eventDescription;

    public Event()
    {

    }
    public Event(String name)
    {
        this.name = name;
        this.id = 0;
    }
}
