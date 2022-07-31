package com.example.b07_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        int auth = this.getIntent().getIntExtra("auth", 0);
        System.out.println("Admin AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        System.out.println(auth);
    }


    public void deleteAllEvents(View view) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Events");



        myRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                System.out.println("why do we exist? just to suffer?");
                if (!task.isSuccessful()) {
                    Log.e("demo", "Error getting data", task.getException());
                }
                else {
                    for (DataSnapshot childSnapshot : task.getResult().getChildren()) {
                        myRef.child(childSnapshot.getKey()).removeValue();

//
                    }


                }
            }
        });
    }

    public void deleteAllUsers(View view){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users");



        myRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                System.out.println("why do we exist? just to suffer?");
                if (!task.isSuccessful()) {
                    Log.e("demo", "Error getting data", task.getException());
                }
                else {
                    for (DataSnapshot childSnapshot : task.getResult().getChildren()) {
                        myRef.child(childSnapshot.getKey()).removeValue();

//
                    }


                }
            }
        });

    }

    public void deleteAllVenues(View view){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Venues");



        myRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                System.out.println("why do we exist? just to suffer?");
                if (!task.isSuccessful()) {
                    Log.e("demo", "Error getting data", task.getException());
                }
                else {
                    for (DataSnapshot childSnapshot : task.getResult().getChildren()) {
                        myRef.child(childSnapshot.getKey()).removeValue();

//
                    }


                }
            }
        });

    }

    public void initializeTwoVenues(View view){
        //BLOCK
        //The code below creates a new venue... temporary until admin view done

        Venue venue = new Venue();
        venue.id = 69;
        venue.capacity = 100;
        venue.name = "notPanam";
        venue.description = "nothing at all";
        ArrayList<String> sports = new ArrayList<String>();
        sports.add("Volleyball");
        sports.add("Quiddich");
        sports.add("Not league");
        sports.add("extreme seven eating");

        venue.sports = sports;

        FirebaseDatabase database = FirebaseDatabase.getInstance();


        DatabaseReference venueRef = database.getReference("Venues");
        venueRef.child(venue.id +"").setValue(venue);

        sports.remove("Volleyball");
        sports.remove("Not league");
        sports.add("League");
        sports.add("Not Valorant");
        venue.description = "amogus poker";
        venue.capacity=12;
        venue.id=7;
        venue.name="7's nostril";
        ArrayList<Integer> scheduledEvents = new ArrayList<Integer>();
        scheduledEvents.add(1);
        scheduledEvents.add(7);
        venue.scheduledEvents = scheduledEvents;

        venueRef.child(venue.id + "").setValue(venue);

//
//event.venueId = 69;
        //ENDBLOCK


    }

    public void transitionToVenues(View view)
    {
        Intent intent = new Intent(this, ChooseVenue.class);
        int auth = this.getIntent().getIntExtra("auth", 0);
        intent.putExtra("auth", auth);
        startActivity(intent);

    }

    public void initializeTwoUsers(View view){

    }

}