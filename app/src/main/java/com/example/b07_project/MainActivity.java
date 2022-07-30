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

import java.util.concurrent.CountDownLatch;

public class MainActivity extends AppCompatActivity {
    Intent startIntent = getIntent();
    String userId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (startIntent != null) userId = getIntent().getStringExtra("userId");
        if (userId == null) userId = "";
    }

    public void transitionToAddEvent(View view)
    {

//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference venueRef = database.getReference("Venues");
//
//        //int venueId = 69;
//
//        CountDownLatch countDownLatch = new CountDownLatch(1);
//
//        Log.i("status", "potential crash site 1");
//
//        venueRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//
//
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//
//                Log.i("status", "potential crash site 2");
//                if (!task.isSuccessful()) {
//                    Log.e("demo", "Error getting data", task.getException());
//                } else {
//
//                    Log.i("status", "adding venues to venues list");
//
//                    for (DataSnapshot childSnapshot:task.getResult().getChildren()){
//                        venues.add(childSnapshot.getValue(Venue.class));
//                    }
//                    countDownLatch.countDown();
//                }
//
//            }
//        });

        Intent addVenue = new Intent(this, ChooseVenue.class);
        addVenue.putExtra("userId", userId);



        startActivity(addVenue);

    }

    public void transitionToAdminActivity(View view){
        Intent intent = new Intent(this, AdminActivity.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }
    public void transitionToAddVenue(View view){
        Intent intent = new Intent(this, AddVenue.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }

    public void transitionToUpComingEvents(View view){
        Intent intent = new Intent(this, UpcomingEventsDriver.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }

    public void transitionToActivityDenny(View view){
        Intent intent = new Intent(this, activityPageDenny.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }

    public void transitionToProfile(View view){
        Intent intent = new Intent(this, profile.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }


}