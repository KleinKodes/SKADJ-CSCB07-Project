package com.example.b07_project.Denny;

import com.google.firebase.database.Exclude;

import java.util.GregorianCalendar;
import java.util.Objects;

public class Event {
    public int id;
    public String name;
    public int capacity;
    public String sport;
    //public GregorianCalendar startTime, endTime;
    public int attendeeNum;
    public String eventDescription;
    public int ownerId;
    public int venueId;
    public int groupId;

    public Long startTimeStamp;
    public Long endTimeStamp;

    @Exclude
    public GregorianCalendar getStartDate() {

        GregorianCalendar date = new GregorianCalendar();
        date.setTimeInMillis(startTimeStamp);

        return date;

    }
    @Exclude
    public void setStartDate(GregorianCalendar date) {
      this.startTimeStamp = date.getTimeInMillis();
      System.out.println(this.getStartDate());
    }

    @Exclude
    public GregorianCalendar getEndDate() {

        GregorianCalendar date = new GregorianCalendar();
        date.setTimeInMillis(endTimeStamp);

        return date;

    }
    @Exclude
    public void setEndDate(GregorianCalendar date) {
        this.endTimeStamp = date.getTimeInMillis();
        System.out.println(this.getEndDate());
    }

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

//    public GregorianCalendar getStartTime() {
//        return startTime;
//    }
//
//    public GregorianCalendar getEndTime() {
//        return endTime;
//    }

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
        this.groupId=-1;
        this.startTimeStamp = 0L;
        this.endTimeStamp = 0L;


    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return id == event.id && attendeeNum == event.attendeeNum && ownerId == event.ownerId && venueId == event.venueId && name.equals(event.name) && Objects.equals(sport, event.sport) && startTimeStamp.equals(event.startTimeStamp) && endTimeStamp.equals(event.endTimeStamp) && Objects.equals(eventDescription, event.eventDescription);
    }

    @Override
    public int hashCode() {
        return id;
    }
}
