package com.example.b07_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class VenueList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue_list);

        ListView listView = findViewById(R.id.venueList);

        ArrayList<Venue> venueList = new ArrayList<>();
        //add info on venueList via firebase
        ArrayList<String> venueNames = new ArrayList<>();
        for(Venue venues: venueList){
            venueNames.add(venues.name);
        }
        ArrayAdapter<?> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, venueNames);
        listView.setAdapter(arrayAdapter);
        //Figure out how to update this information dynamically.
    }
}