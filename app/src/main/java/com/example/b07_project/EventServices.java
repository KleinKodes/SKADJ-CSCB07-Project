package com.example.b07_project;

import android.app.Activity;
import android.os.Handler;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.Instant;
import java.util.ArrayList;

public class EventServices {

    FirebaseDatabase database;
    DatabaseReference eventRef;
    DatabaseReference userRef;
    DatabaseReference venueRef;
    UserServices userServices;
    public EventServices(){
        this.database = FirebaseDatabase.getInstance();
        this.eventRef = database.getReference("Events");
        this.userRef = database.getReference("Users");
        this.venueRef = database.getReference("Venues");
        this.userServices = new UserServices();

    }

    public void deleteEventById(int eventId){

        UserServices userServices = new UserServices();

        eventRef.child(eventId + "").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                Event event = task.getResult().getValue(Event.class);
                if (event == null) return;
                DatabaseReference scheduledEventsRef = venueRef.child(event.venueId + "").child("scheduledEvents");
                        scheduledEventsRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.getResult().getValue() != null) {
                            ArrayList<Integer> scheduledEvents = (ArrayList<Integer>) task.getResult().getValue();

                            if (scheduledEvents.contains(eventId)) scheduledEvents.remove(scheduledEvents.indexOf(eventId));
                            scheduledEventsRef.setValue(scheduledEvents);

                            if (event.attendees != null) {
                                for (String i : event.attendees) {
                                    userServices.removeUserFromEvent(i, eventId);
                                }
                            }
                            eventRef.child(eventId + "").removeValue();
                        }
                    }
                });

            }
        });


    }

    public Event findEventById(int eventId){
        for (Event e: getAllEvents()) if (e.getId() == eventId) return e;
        return null;
    }

    public ArrayList<Event> getUpcomingEvents() {
        ArrayList<Event> searchResults = new ArrayList<Event>();
        for (Event e: getAllEvents()) if (e.approved) searchResults.add(e);
        return searchResults;

    }

    public ArrayList<Integer> getUpcomingEventIds () {

        ArrayList<Integer> searchResults = new ArrayList<Integer>();
        for (Event e: getUpcomingEvents()) searchResults.add(e.getId());
        return searchResults;

    }

    public ArrayList<Event> getAllEvents() {
        final ArrayList<Event>[] allEvents = new ArrayList[]{new ArrayList<Event>()};
        final Boolean done[] = {false};
        eventRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

               allEvents[0] = (ArrayList<Event>) task.getResult().getValue();
               done[0] = true;

            }
        });

        while (!done[0]){
            try{
                wait(10);

            }catch (Exception e){
                Log.e("EventServicesError", e.getMessage());
            }
        }

        return allEvents[0];
    }

    public ArrayList<Event> getUnapprovedEvents(){
        ArrayList<Event> searchResults = new ArrayList<Event>();
        for (Event i: getAllEvents()) if (!i.approved) searchResults.add(i);
        return searchResults;
    }

    public ArrayList<Integer> getUnapprovedEventIds() {
        ArrayList<Integer> searchResults = new ArrayList<Integer>();
        for (Event i: getAllEvents()) if (!i.approved) searchResults.add(i.getId());
        return searchResults;

    }

    public void addEvent(Activity activity, View view, Event event, Venue venue){


        if(view != null && validateEvent(view, event, venue)) {

            DatabaseReference scheduledEvents = venueRef.child(event.venueId + "").child("scheduledEvents");
                    scheduledEvents.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    ArrayList<Integer> scheduledEventsList = (ArrayList<Integer>) task.getResult().getValue();
                    if (scheduledEventsList == null) scheduledEventsList = new ArrayList<Integer>();


                    ArrayList<Integer> finalScheduledEventsList = scheduledEventsList;
                    eventRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            ArrayList<Event> allEventsList = new ArrayList<Event>();
                            for (DataSnapshot childSnapshot : task.getResult().getChildren()){
                                allEventsList.add(childSnapshot.getValue(Event.class));
                            }

                            event.ownerId = userServices.getCurrentUserId();
                            event.id = findMaxEventId(allEventsList);
                            finalScheduledEventsList.add(event.getId());
                            scheduledEvents.setValue(finalScheduledEventsList);

                            eventRef.child(event.getId() + "").setValue(event);
                            activity.finish();
                        }
                    });

                }
            });
        }



    }

    public int findMaxEventId(ArrayList<Event> eventArrayList){
        int curMax = -1;
        for (Event e: eventArrayList) {
            if (e.getId() != curMax + 1) return curMax + 1;
            else if (e.getId() > curMax) curMax = e.getId();
        }
            return curMax + 1;
    }

    public Boolean validateEvent(View view, Event event, Venue venue){


            Log.i("status", "validating event");
            if (event.getStartTimeStamp() < Instant.now().toEpochMilli()) {
                //makePopUp(view, "Invalid start date");
                Snackbar mySnackbar = Snackbar.make(view, "Invalid start date", BaseTransientBottomBar.LENGTH_SHORT);
                mySnackbar.show();
                return false;
            }

            if (event.getEndTimeStamp() < event.getStartTimeStamp()) {
                //makePopUp(view, "Invalid end date");
                Snackbar mySnackbar = Snackbar.make(view, "Invalid end date", BaseTransientBottomBar.LENGTH_SHORT);
                mySnackbar.show();
                return false;
            }

            if (event.getCapacity() < 10){
                //makePopUp(view, "Capacity too low");
                Log.i("status", "capacity low, should give popup");
                Snackbar mySnackbar = Snackbar.make(view, "Capacity too low", BaseTransientBottomBar.LENGTH_SHORT);
                mySnackbar.show();
                return false;
            }

            if (event.getName().trim().isEmpty() || event.getName().trim() == "Name"){
                //makePopUp(view, "Please give your event a name");
                Snackbar mySnackbar = Snackbar.make(view, "Please give your event a name", BaseTransientBottomBar.LENGTH_SHORT);
                mySnackbar.show();
                return false;
            }
            if (event.getEventDescription().length() < 20){
                //makePopUp(view, "Please give your event a good description.");
                Snackbar mySnackbar = Snackbar.make(view, "Please give your event a good description", BaseTransientBottomBar.LENGTH_SHORT);
                mySnackbar.show();
                return false;
            }



                    if((venue != null) && (event.capacity > venue.capacity))
                    {
                        Snackbar mySnackbar = Snackbar.make(view, "CAPACITY OVERLOAD!!! (Capacity is larger than venue's max)", BaseTransientBottomBar.LENGTH_SHORT);
                        mySnackbar.show();
                        return false;
                    }


            return true;



    }

    public ArrayList<Event> searchEventsByName(String name){

        ArrayList<Event> searchResults = new ArrayList<Event>();
        for (Event i: getUpcomingEvents()) if (i.getName().contains(name)) searchResults.add(i);
        return searchResults;

    }

    public ArrayList<Event> filterEventsByVenueId(int venueId) {
        ArrayList<Event> searchResults = new ArrayList<Event>();
        for (Event i: getUpcomingEvents()) if (i.getVenueId() == venueId) searchResults.add(i);
        return searchResults;

    }

    public ArrayList<String> getAllEventAttendees(int eventId) {
        ArrayList<String> searchResults = new ArrayList<String>();
        for (String id : findEventById(eventId).attendees) searchResults.add(userServices.findUserByUserId(id).getFullName());
        return searchResults;
    }

    public void ViewEventAttendees(int eventId, Context context){

        eventRef.child(eventId + "").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {


                ArrayList<String> attendeeList = new ArrayList<String>();
                for (String userId : task.getResult().getValue(Event.class).attendees){
                    userRef.child(userId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {

                            attendeeList.add(task.getResult().getValue(User.class).getFullName());
                        }
                    });
                }

                Intent intent = new Intent(context, profile.class); //change this from profile LOL
                intent.putExtra("attendeeList", attendeeList);
                context.startActivity(intent);


            }
        });

    }

    public void purgeOldEvents(){
        eventRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                ArrayList<Event> allEventsList = new ArrayList<Event>();
                for (DataSnapshot childSnapshot : task.getResult().getChildren()){
                    allEventsList.add(childSnapshot.getValue(Event.class));
                }

                for (Event event :allEventsList) if (event.getEndTimeStamp() < Instant.now().toEpochMilli()) deleteEventById(event.getId());
            }
        });
    }




}
