package com.example.b07_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {

    int auth;
    String firstName;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        UserServices userServices = new UserServices();
        auth = userServices.getCurrentUserAuth();
        ////    System.out.println(auth);
    }

    


    public void signOut(View view){
        FirebaseAuth.getInstance().signOut();
        Intent login = new Intent(AdminActivity.this, LoginActivity.class);
        startActivity(login);
        Intent tempor = getIntent();
        tempor.putExtra("auth", -1);
        tempor.putExtra("id", -1);
        finish();
    }

    



    public void deleteAllEvents(View view) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Events");



        myRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("demo", "Error getting data", task.getException());
                }
                else {
                    for (DataSnapshot childSnapshot : task.getResult().getChildren()) {
                        myRef.child(childSnapshot.getKey()).removeValue();
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
                if (!task.isSuccessful()) {
                    Log.e("demo", "Error getting data", task.getException());
                }
                else {
                    for (DataSnapshot childSnapshot : task.getResult().getChildren()) {
                        myRef.child(childSnapshot.getKey()).removeValue();
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
                if (!task.isSuccessful()) {
                    Log.e("demo", "Error getting data", task.getException());
                }
                else {
                    for (DataSnapshot childSnapshot : task.getResult().getChildren()) {
                        myRef.child(childSnapshot.getKey()).removeValue();
                    }


                }
            }
        });

    }

    public void initializeTwoVenues(View view){
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


    }

    public void transitionToVenues(View view)
    {
        Intent intent = new Intent(this, ChooseVenue.class);
        intent.putExtra("auth", auth);
        intent.putExtra("userId", userId);
        intent.putExtra("firstName", firstName);

        startActivity(intent);

    }

    public void transitionToAddVenue(View view)
    {
        Intent intent = new Intent(this, AddVenue.class);
        UserServices userServices = new UserServices();
        int auth = userServices.getCurrentUserAuth();
        intent.putExtra("auth", 1);
        intent.putExtra("mode", 0);
        startActivity(intent);
    }

    public void transitionToEvents(View view)
    {

        Intent intent = new Intent(this, UpcomingEventsDriver.class);
        intent.putExtra("auth", auth);
        intent.putExtra("userId", userId);
        intent.putExtra("firstName", firstName);
        startActivity(intent);



    }

    public void transtionToUnapprovedEvents(View view)
    {

    }

    public void transitionToApproveEvents(View view){
        Intent intent = new Intent(this, activityPageDenny.class);
        intent.putExtra("auth", auth);
        intent.putExtra("userId", userId);
        intent.putExtra("firstName", firstName);
        intent.putExtra("approvalNeeded", true);
        startActivity(intent);

    }

    public void logOut(View view){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();
    }

}