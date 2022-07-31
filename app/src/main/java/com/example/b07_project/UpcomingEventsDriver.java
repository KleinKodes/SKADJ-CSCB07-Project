package com.example.b07_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class UpcomingEventsDriver extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_events_driver);

        //below shows upcoming events, and upcoming events by venue if specified
        Bundle test = getIntent().getExtras();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("Events");

        RecyclerView listVenues = (RecyclerView) findViewById(R.id.upcomingEventsList);
        UpcomingEventsAdapter adapter = new UpcomingEventsAdapter(new ArrayList<Event>());

        listVenues.setAdapter(adapter);
        listVenues.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        Log.i("status", "created adapter and recycleview");

        if (test.size() > 1) {
            //String testTwo = test.getString("key");
            //Log.d("test", testTwo);
            database.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ArrayList<Event> Events = new ArrayList<>();
                    String inputId = test.getString("key");
                    for (DataSnapshot data : snapshot.getChildren()) {
                        String compareId = data.child("venueId").getValue().toString();
                        if(Objects.equals(inputId, compareId)){
                            String name = data.child("name").getValue().toString();
                            Event event = new Event(name);
                            Events.add(event);
                        }
                        else{continue;}
                    }

                    ArrayList<Event> upcomingEvents = new ArrayList<>();
                    ArrayList<Event> copyOfEvents = new ArrayList<>(Events);
                    adapter.setUpcomingEventsAdapterList(upcomingEvents);
                    while (upcomingEvents.size() < Events.size()){
                        Event newEvent = null;
                        for(Event each: copyOfEvents) {
                            if (newEvent == null || newEvent.startTimeStamp > each.startTimeStamp) {
                                newEvent = each;
                            }
                        }
                        upcomingEvents.add(newEvent);
                        copyOfEvents.remove(newEvent);
                        adapter.notifyItemInserted(adapter.getItemCount());
                    }

                    for (Event i: upcomingEvents) Log.i("arrayList Item", i.getName());

                    adapter.notifyDataSetChanged();

                    Log.i("status", "set adapter list5");

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } else {
            database.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ArrayList<Event> Events = new ArrayList<>();
                    for (DataSnapshot data : snapshot.getChildren()) {
                        String name = data.child("name").getValue().toString();
                        Event event = new Event(name);
                        event.startTimeStamp = Long.valueOf(data.child("startTimeStamp").getValue().toString());
                        Events.add(event);
                    }

                    ArrayList<Event> upcomingEvents = new ArrayList<>();
                    ArrayList<Event> copyOfEvents = new ArrayList<>(Events);
                    adapter.setUpcomingEventsAdapterList(upcomingEvents);
                    while (upcomingEvents.size() < Events.size()){
                        Event newEvent = null;
                        for(Event each: copyOfEvents) {
                            if (newEvent == null || newEvent.startTimeStamp > each.startTimeStamp) {
                                newEvent = each;
                            }
                        }
                        upcomingEvents.add(newEvent);
                        copyOfEvents.remove(newEvent);
                        adapter.notifyItemInserted(adapter.getItemCount());


                    }

                    for (Event i: upcomingEvents) Log.i("arrayList Item", i.getName());

                    Log.i("status", "set adapter list");
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            //Adds and displays the upcoming events for the user.
        }
    }

    public void onFilterByVenue(View v){
        Intent I = new Intent(this, filterUpcomingByVenue.class);
        finish();
        startActivity(I);
    }

    public void sortBy(View v){
        Intent I = new Intent(this, UpcomingEventsDriver.class);
        finish();
        startActivity(I);
    }
}