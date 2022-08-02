package com.example.b07_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class VenuePageDennt extends AppCompatActivity {
    public static String venueName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue_page_dennt);

        View home = findViewById(R.id.homeButton);
        home.setOnClickListener(new Navigation());
        View profile = findViewById(R.id.profileButton);
        profile.setOnClickListener(new Navigation());
    }

    public void addCard(View view) {
        LinearLayout layout = (LinearLayout) findViewById(R.id.venuePageCardList);
        View v = LayoutInflater.from(this).inflate(R.layout.venue_place_card, null);
        layout.addView(v);
    }

    public void transitionToVenueDesc(View view) {
        Intent intent = new Intent(this, VenueDesc.class);
        TextView venue = (TextView) ((ViewGroup) view).getChildAt(0);
        intent.putExtra(venueName, venue.getText());
        startActivity(intent);
    }
}