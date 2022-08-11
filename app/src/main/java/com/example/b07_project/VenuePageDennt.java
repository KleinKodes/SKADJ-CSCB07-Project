package com.example.b07_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.stream.Stream;

public class VenuePageDennt extends AppCompatActivity {
    private int pStatus = 0;
    public static String venueName;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    UserServices userServices;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue_page_dennt);

        userServices = new UserServices();

        View home = findViewById(R.id.homeButton);
        home.setOnClickListener(new Navigation());
        View profile = findViewById(R.id.profileButton);
        profile.setOnClickListener(new Navigation());
        View logout = findViewById(R.id.logOutButton);
        logout.setOnClickListener(new Navigation());

        TextView userName = findViewById(R.id.profileUserName);
        userName.setText(userServices.getCurrentUserName());


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Venues");
        initialzieVenues();
    }
//
//    public void addCard(View view) {
//        LinearLayout layout = (LinearLayout) findViewById(R.id.venuePageCardList);
//        View v = LayoutInflater.from(this).inflate(R.layout.venue_place_card, null);
//        layout.addView(v);
//    }

    public void initialzieVenues() {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int counter = 0;
                for (DataSnapshot snap : snapshot.getChildren()) {
                    Venue venue = snap.getValue(Venue.class);
                    counter ++;
                    ////    System.out.println("Venues: "+venue.getName());
                    addVenues(venue.getName(), venue.getId(), counter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                DatabaseError e = error;
                System.out.println(e.getCode());
            }
        });

    }

    public void addVenues(String name, int venueId, int count){
        LinearLayout layout = (LinearLayout) findViewById(R.id.venuePageCardList);
        View v = LayoutInflater.from(this).inflate(R.layout.venue_place_card, null);
        layout.addView(v);
        int index = layout.getChildCount()-1;
        TextView newCard = (TextView) ((ViewGroup)((ViewGroup)layout.getChildAt(index)).getChildAt(0)).getChildAt(0);
        newCard.setText(name);

        ViewGroup buttonGroup = (ViewGroup) ((ViewGroup)((ViewGroup)layout.getChildAt(index)).getChildAt(0)).getChildAt(1);
        buttonGroup.getChildAt(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), VenueDesc.class);
                intent.putExtra(venueName, newCard.getText());
                startActivity(intent);
            }
        });

        buttonGroup.getChildAt(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), activityPageDenny.class);
                intent.putExtra("venueId", venueId);
                intent.putExtra("index", count);
                startActivity(intent);
            }
        });
    };

    public void transitionToEvents(View view){

    }


//    public void transitionToVenueDesc(View view) {
//        Intent intent = new Intent(this, VenueDesc.class);
//        TextView venue = (TextView) ((ViewGroup) view).getChildAt(0);
//        intent.putExtra(venueName, venue.getText());
//        startActivity(intent);
//    }
}