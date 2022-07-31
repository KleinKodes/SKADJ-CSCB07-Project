package com.example.b07_project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class VenueDesc extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue_desc);
        Intent intent = getIntent();
        String venueName = intent.getStringExtra(VenuePageDennt.venueName);
        TextView venueText = (TextView) findViewById(R.id.venueDescVenue);
        venueText.setText(venueName);
    }

}