package com.example.b07_project;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class VenueDesc extends AppCompatActivity {
    String venueName;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    UserServices userServices;
    private int pStatus=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_activity); // FIX
        ProgressBar p = (ProgressBar)findViewById(R.id.progressBar);
        Handler h = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(pStatus < 100) {
                    pStatus++;
                    android.os.SystemClock.sleep(10);
                    h.post(new Runnable() {
                        @Override
                        public void run() {
                            p.setProgress(pStatus);
                        }
                    });
                }
            }
        }).start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run(){
                setContentView(R.layout.activity_venue_desc);
                Intent intent = getIntent();
                venueName = intent.getStringExtra(VenuePageDennt.venueName);
                TextView venueText = (TextView) findViewById(R.id.venueDescVenue);
                venueText.setText(venueName);

                View home = findViewById(R.id.homeButton);
                home.setOnClickListener(new Navigation());
                View profile = findViewById(R.id.profileButton);
                profile.setOnClickListener(new Navigation());
                View logout = findViewById(R.id.logOutButton);
                logout.setOnClickListener(new Navigation());

                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference("Venues");
                initializeInfo();
            }
        }, 1200);
    }

    public void initializeInfo(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    Venue venue = snap.getValue(Venue.class);
                    System.out.printf("%s %s\n",venue.getName(), venueName);
                    if(venue.getName().equals(venueName.trim())){
                        overwriteInfo(venue);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println(error);
            }
        });
    }

    public void overwriteInfo(Venue venue){
        TextView streetAddress = (TextView) findViewById(R.id.venueDescStreetAddress);
        TextView city = (TextView) findViewById(R.id.venueDescCity);
        TextView province = (TextView) findViewById(R.id.venueDescProvince);
        TextView postal = (TextView) findViewById(R.id.venueDescPostal);
        TextView country = (TextView) findViewById(R.id.venueDescCountry);
        TextView description = (TextView) findViewById(R.id.venueDescDescription);

        TextView monday = (TextView) findViewById(R.id.venueDescMonday);
        TextView tuesday = (TextView) findViewById(R.id.venueDescTuesday);
        TextView wednesday = (TextView) findViewById(R.id.venueDescWednesday);
        TextView thursday = (TextView) findViewById(R.id.venueDescThursday);
        TextView friday = (TextView) findViewById(R.id.venueDescFriday);
        TextView saturday = (TextView) findViewById(R.id.venueDescSaturday);
        TextView sunday = (TextView) findViewById(R.id.venueDescSunday);


        TextView start = (TextView) findViewById(R.id.venueDescStart);
        TextView end = (TextView) findViewById(R.id.venueDescEnd);

        monday.setText(String.format("Monday: %s", (venue.daysAvailable.charAt(0) == '0') ? "Not Available":"Available"));
        tuesday.setText(String.format("Tuesday: %s", (venue.daysAvailable.charAt(1) == '0') ? "Not Available":"Available"));
        wednesday.setText(String.format("Wednesday: %s", (venue.daysAvailable.charAt(2) == '0') ? "Not Available":"Available"));
        thursday.setText(String.format("Thursday: %s", (venue.daysAvailable.charAt(3) == '0') ? "Not Available":"Available"));
        friday.setText(String.format("Friday: %s", (venue.daysAvailable.charAt(4) == '0') ? "Not Available":"Available"));
        saturday.setText(String.format("Saturday: %s", (venue.daysAvailable.charAt(5) == '0') ? "Not Available":"Available"));
        sunday.setText(String.format("Sunday: %s", (venue.daysAvailable.charAt(6) == '0') ? "Not Available":"Available"));

        streetAddress.setText(venue.getAddress().streetAddress);
        city.setText(venue.getAddress().city);
        province.setText(venue.getAddress().state);
        postal.setText(venue.getAddress().postalCode);
        country.setText(venue.getAddress().country);
        description.setText(venue.getDescription());

        start.setText(String.format("Start Time %s:%s",venue.availableFrom.getHour(),venue.availableFrom.getMin()));
        end.setText(String.format("End Time %s:%s",venue.availableTo.getHour(),venue.availableFrom.getMin()));
    }

    public void goBackToVenuePage(View view){
        super.onBackPressed();
    }

}