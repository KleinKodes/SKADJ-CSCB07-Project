package com.example.b07_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class filterUpcomingByVenue extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_filter_upcoming_by_venue_deprecated);
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void onEnter(View view){
        Intent newIntent = new Intent(this, UpcomingEventsDriver.class);
        DatabaseReference databaseVenues = FirebaseDatabase.getInstance().getReference().child("Venues");
        databaseVenues.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Spinner mySpinner = (Spinner) findViewById(R.id.spinner);
                String message = mySpinner.getSelectedItem().toString();

                String id = ""; 
                String venueName = ""; 
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
                newIntent.putExtra("pos", getIntent().getIntExtra("pos", 0));

                startActivity(newIntent);
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}