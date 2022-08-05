package com.example.b07_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class UpcomingEventsDriver extends AppCompatActivity {
    int auth;
    String firstName;
    String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_events_driver);

        filterUpcomingByVenue filter = new filterUpcomingByVenue();
        setSpinner();

        //below shows upcoming events, and upcoming events by venue if specified
        Bundle test = getIntent().getExtras();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("Events");

        RecyclerView listVenues = (RecyclerView) findViewById(R.id.upcomingEventsList);
        UpcomingEventsAdapter adapter = new UpcomingEventsAdapter(new ArrayList<Event>());

        listVenues.setAdapter(adapter);
        listVenues.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        Log.i("status", "created adapter and recycleview");

        if (test.get("key") != null) {
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
                        //Denny added this
                        Event event = data.getValue(Event.class);
                        Events.add(event);
//                        String name = data.child("name").getValue().toString();
//                        Event event = new Event(name);
//                        event.startTimeStamp = Long.valueOf(data.child("startTimeStamp").getValue().toString());
//                        Events.add(event);
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

    public void setSpinner(){
        // create spinner for list of venues
        Intent testIntent = new Intent(this, filterUpcomingByVenue.class);
        DatabaseReference databaseVenues = FirebaseDatabase.getInstance().getReference().child("Venues");
        databaseVenues.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> venueNames = new ArrayList<>();
                for(DataSnapshot data: snapshot.getChildren()){
                    String venueName = data.child("name").getValue().toString() + " id:" + data.child("id").getValue().toString();
                    venueNames.add(venueName);
                }
                Spinner spinner = (Spinner)findViewById(R.id.spinner);


                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, venueNames);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
                //spinner.setPrompt("Select");
                spinner.setSelection(getIntent().getIntExtra("pos", 0));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void onEnter(View view){
        Intent newIntent = new Intent(this, UpcomingEventsDriver.class);

        //Make sure the name is a valid venue
        DatabaseReference databaseVenues = FirebaseDatabase.getInstance().getReference().child("Venues");
        databaseVenues.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Spinner mySpinner = (Spinner) findViewById(R.id.spinner);
                String message = mySpinner.getSelectedItem().toString();
                int pos = mySpinner.getSelectedItemPosition();

                String id = ""; // id string
                String venueName = ""; // venue string
                String [] listStrings = message.split(" ");
                for(int i = 0; i < listStrings.length; i++){
                    if(i+1 == listStrings.length){id = listStrings[i];}
                    else if(i+2 == listStrings.length){venueName+=listStrings[i];}
                    else{venueName+=listStrings[i] + " ";}
                }
                String[] listId = id.split(":");
                String finalId = listId[1];

                newIntent.putExtra("key", finalId);
                newIntent.putExtra("auth", 1);
                newIntent.putExtra("pos", pos); //spinner position
                startActivity(newIntent);
                overridePendingTransition(0, 0);
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}