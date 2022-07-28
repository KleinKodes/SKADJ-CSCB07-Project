package com.example.b07_project;

import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    DatabaseReference database;
    static ArrayList<Venue> venueList = new ArrayList<>();
    static ArrayList<Venue> demoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView listVenues = (RecyclerView) findViewById(R.id.venueList);

        /*database = FirebaseDatabase.getInstance().getReference("Venues");

        database.child("Venues").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for(DataSnapshot postSnapshotOne : snapshot.getChildren()) {
                    boolean verifyExistsOne = (Boolean) postSnapshotOne.getValue();
                    int id = 0;
                    String name = "";
                    String description = "";
                    Address address = null;
                    ArrayList<String> sports = new ArrayList<>();
                    int capacity = 0;
                    if (verifyExistsOne) {
                        for (DataSnapshot postSnapshotTwo : postSnapshotOne.getChildren()) {
                            if(Objects.equals(postSnapshotTwo.getKey(), "capacity")){capacity = (int)postSnapshotTwo.getValue();}
                            if(Objects.equals(postSnapshotTwo.getKey(), "description")){description = (String)postSnapshotTwo.getValue();}
                            if(Objects.equals(postSnapshotTwo.getKey(), "id")){id = (int)postSnapshotTwo.getValue();}
                            if(Objects.equals(postSnapshotTwo.getKey(), "name")){name = (String)postSnapshotTwo.getValue();}
                            if(Objects.equals(postSnapshotTwo.getKey(), "sports")){
                                for(DataSnapshot eachSport : postSnapshotTwo.getChildren()){
                                    sports.add((String)eachSport.getValue());
                                }
                            }
                        }
                    }
                    Venue newVenue = new Venue(id, name, description, address, sports, capacity);
                    venueList.add(newVenue);
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {}
        });*/
        ArrayList<String> demoSports = new ArrayList<>();
        demoSports.add("Hockey");
        demoSports.add("Tennis");
        Venue newVenue = new Venue(10, "Test", "This is a test.", null, demoSports, 10);
        demoList.add(newVenue);

        VenueAdapter adapter = new VenueAdapter(demoList);
        listVenues.setAdapter(adapter);
        listVenues.setLayoutManager(new LinearLayoutManager(this));

        /*add info on venueList via firebase
        ArrayList<String> venueNames = new ArrayList<>();
        for(Venue venues: venueList){
            venueNames.add(venues.getName());
        }
        venueNames.add("Testing");
        venueNames.add("Tester");*/
    }

    public void transitionToAddEvent(View view)
    {
        Intent addEvent = new Intent(this, AddEvent.class);
        startActivity(addEvent);
    }

}