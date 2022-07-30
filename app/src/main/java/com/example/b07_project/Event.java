package com.example.b07_project;

import com.google.firebase.database.Exclude;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;

public class Event {
    public int id;
    public String name;
    public int capacity;
    public String sport;
    public int attendeeNum;
    public String eventDescription;
    public int ownerId;
    public int venueId;
    public int groupId;
    public ArrayList<Integer> attendees;

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

    public long getStartTimeStamp() {return startTimeStamp;}

    public long getEndTimeStamp() {return endTimeStamp;}

    public String getStartDateString() {

        GregorianCalendar calendar = this.getStartDate();


        return calendar.get(Calendar.DAY_OF_MONTH) + "/"+ calendar.get(Calendar.MONTH) + "/" + calendar.get(Calendar.YEAR);
    }

    public String getStartTimeString() {

        GregorianCalendar calendar = this.getStartDate();


        return calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);
    }

    public String getEndTimeString() {

        GregorianCalendar calendar = this.getEndDate();

        return calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);
    }

    public String getEndDateString() {

        GregorianCalendar calendar = this.getEndDate();


        return calendar.get(Calendar.DAY_OF_MONTH) + "/"+ calendar.get(Calendar.MONTH) + "/" + calendar.get(Calendar.YEAR);
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
        this.attendees = new ArrayList<Integer>();


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
