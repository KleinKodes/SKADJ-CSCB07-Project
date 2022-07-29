package com.example.b07_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //below is an attempt to query firebase data
        /*DatabaseReference database = FirebaseDatabase.getInstance().getReference("Venues");

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
    }

    public void transitionToAddEvent(View view)
    {
        Intent addEvent = new Intent(this, AddEvent.class);
        startActivity(addEvent);
    }

    public void transitionToUpcomingEvents(View view){
        Intent upcoming = new Intent(this, UpcomingEventsDriver.class);
        startActivity(upcoming);
    }

}