package com.example.b07_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class EventList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        ListView listView = findViewById(R.id.eventList);

        ArrayList<Event> eventList = new ArrayList<>();
        //add info on venueList via firebase
        ArrayList<String> eventNames = new ArrayList<>();
        for(Event events: eventList){
            eventNames.add(events.name + "(" + events.sport + ")");
        }
        ArrayAdapter<?> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, eventNames);
        listView.setAdapter(arrayAdapter);
        //Figure out how to update this information dynamically.
    }
}