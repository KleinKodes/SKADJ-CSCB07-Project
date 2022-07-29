package com.example.b07_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UpcomingEventsDriver extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_events_driver);

        //query from firebase change later to query from user (when user class is made)
        DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("Events");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Event> Events = new ArrayList<>();
                for(DataSnapshot data: snapshot.getChildren()){
                    String name = data.child("name").getValue().toString();
                    Event event = new Event(name);
                    Events.add(event);
                }
                RecyclerView listVenues = (RecyclerView) findViewById(R.id.upcomingEventsList);
                UpcomingEventsAdapter adapter = new UpcomingEventsAdapter(Events);
                listVenues.setAdapter(adapter);
                listVenues.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //Adds and displays the upcoming events for the user.
    }
}