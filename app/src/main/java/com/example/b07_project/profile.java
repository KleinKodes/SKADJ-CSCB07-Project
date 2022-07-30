package com.example.b07_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class profile extends AppCompatActivity {
    public static String tabState = "";
    public static String userId;
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);



        Intent intent = getIntent();
        int state = intent.getIntExtra(profile.tabState,1);
        userId = intent.getStringExtra("userId");
        if (userId == null) userId = "";
        Log.i("userid ", userId);
        System.out.println(state);





        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference eventRef = database.getReference("Events");
        DatabaseReference userRef = database.getReference("Users");
        DatabaseReference venueRef = database.getReference("Venues");

        eventRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                Event event;
                //((ViewGroup) findViewById(R.id.profilePage)).removeView(findViewById(R.id.sampleEventCard));
                for(DataSnapshot i : task.getResult().getChildren()) {
                    event = i.getValue(Event.class);
                    if (event.ownerId == null) event.ownerId = "";
                    Log.i("ownerId", event.getOwnerId());



                    if (state == 1 && event.getOwnerId() != null && event.getOwnerId().equals(userId)) {

                        Log.i("status", "found a owner match");

                        Event finalEvent = event;


                        userRef.child(event.getOwnerId() + "").child("firstName").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                String hostName = task.getResult().getValue(String.class);

                                venueRef.child(finalEvent.venueId + "").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                                        Venue venue = task.getResult().getValue(Venue.class);
                                        addProfileCard(findViewById(R.id.profilePage), finalEvent, hostName, venue.getName());
                                    }
                                });


                            }
                        });


                    }

                    if (state == 2 && event.attendees != null && event.attendees.contains(userId)) {

                        Event finalEvent = event;

                        Log.i("status", "found a attendee match");


                        userRef.child(event.getOwnerId() + "").child("firstName").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                String hostName = task.getResult().getValue(String.class);

                                venueRef.child(finalEvent.venueId + "").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                                        Venue venue = task.getResult().getValue(Venue.class);
                                        addProfileCard(findViewById(R.id.profilePage), finalEvent, hostName, venue.getName());
                                    }
                                });


                            }
                        });


                    }
                }

            }
        });





        ConstraintLayout layout;
        if (state == 1){
            layout = (ConstraintLayout) findViewById(R.id.tab1);
        }
        else{
            layout = (ConstraintLayout) findViewById(R.id.tab2);
        }
        layout.setBackgroundTintList(ContextCompat.getColorStateList(this,R.color.dark_green));
    }

    public void addProfileCard(View view, Event event, String hostName, String venueName) {
        LinearLayout layout = (LinearLayout) findViewById(R.id.cardList);
        View v = LayoutInflater.from(this).inflate(R.layout.profile_card, null);
        TextView textView = v.findViewById(R.id.profileHostName);
        textView.setText(hostName);
        textView = v.findViewById(R.id.profileDate);
        textView.setText(event.getStartDateString());
        textView = v.findViewById(R.id.profileStartTime);
        textView.setText(event.getStartTimeString());
        //textView = v.findViewById(R.id.profileEndTi);
        //textView.setText(event.getEndTimeString());
        textView = v.findViewById(R.id.profileVenueName);
        textView.setText(venueName);
        textView = v.findViewById(R.id.profileEventName);
        textView.setText(event.getName());
        textView.setHint(event.getId() + "");
        layout.addView(v);
    }

    public void switchToCreate(View view) {
        Intent addProfile = new Intent(this, profile.class);
        addProfile.putExtra(tabState, 1);
        startActivity(addProfile);
    }

    public void switchToJoin(View view) {
        Intent addProfile = new Intent(this, profile.class);
        addProfile.putExtra(tabState, 2);
        startActivity(addProfile);
    }
}