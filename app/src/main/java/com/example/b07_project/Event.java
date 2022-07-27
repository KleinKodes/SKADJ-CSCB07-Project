package com.example.b07_project;

import java.util.Date;
import java.util.Objects;

public class Event {
    public int id;
    public String name;
    public int capacity;
    public String sport;
    public Date startTime, endTime;
    public int attendeeNum;
    public String eventDescription;
    public int ownerId;
    public int venueId;

    public int getCapacity(){
        return capacity;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSport() {
        return sport;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public int getAttendeeNum() {
        return attendeeNum;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public int getVenueId() {
        return venueId;
    }



    public Event()
    {

    }
    public Event(String name)
    {
        this.name = name;
        this.id = 0;
        this.ownerId=0;
        this.venueId=0;
        this.attendeeNum=0;
        this.capacity = 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return id == event.id && attendeeNum == event.attendeeNum && ownerId == event.ownerId && venueId == event.venueId && name.equals(event.name) && Objects.equals(sport, event.sport) && Objects.equals(startTime, event.startTime) && Objects.equals(endTime, event.endTime) && Objects.equals(eventDescription, event.eventDescription);
    }

    @Override
    public int hashCode() {
        return id;
    }
}
