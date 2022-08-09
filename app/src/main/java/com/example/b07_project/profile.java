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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class profile extends AppCompatActivity {
    public static String tabState = "";
    public static String userId;
    public UserServices userServices;
    public EventServices eventServices;
    public int state;
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        userServices = new UserServices();
        eventServices = new EventServices();


        Intent intent = getIntent();
        state = intent.getIntExtra(profile.tabState,1);
        userId = userServices.getCurrentUserId();
        if (userId == null) userId = "";
        Log.i("userid ", userId);
        System.out.println(state);

        View home = findViewById(R.id.homeButton);
        home.setOnClickListener(new Navigation());
        View profile = findViewById(R.id.profileButton);
        profile.setOnClickListener(new Navigation());
        View logout = findViewById(R.id.logOutButton);
        logout.setOnClickListener(new Navigation());





        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference eventRef = database.getReference("Events");
        DatabaseReference userRef = database.getReference("Users");
        DatabaseReference venueRef = database.getReference("Venues");

        TextView profileName = findViewById(R.id.profileUserName);
        profileName.setText(userServices.getCurrentUserName());

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
        layout.setBackgroundTintList(ContextCompat.getColorStateList(this,R.color.light_green));
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
        Button button = v.findViewById(R.id.profileDelete);
        Button buttonAttendee = v.findViewById(R.id.profileAttendee);
        button.setHint(event.getId() + "");
        buttonAttendee.setHint(event.getId() + "");
        textView = v.findViewById(R.id.profileApproval);

        if (state == 2){
            buttonAttendee.setVisibility(View.GONE);
        }
        View leftHalf = v.findViewById(R.id.linearLayout2);
        leftHalf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent eventIntent = new Intent(getBaseContext(), ActivityDesc.class);
                if (state == 1) eventIntent.putExtra("isThisMyEvent", true);
                else eventIntent.putExtra("isThisMyEvent", false);
                eventIntent.putExtra("eventId", event.getId());
                eventIntent.putExtra("hostName", hostName);
                eventIntent.putExtra("venueName", venueName);
                startActivity(eventIntent);
            }
        });

        if(event.getApproved() == true){textView.setVisibility(View.GONE);}
        else {textView.setText("Waiting For Approval...");}

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (state == 2) {
                    Log.i("status", "about to remove user with uid " + userId +
                            " from event with id " + event.getId() +
                            " with the name " + event.getName());
                    userServices.removeUserFromEvent(userId, event.getId());
                    //switchToCreate(view);
                }else if (state == 1){
                    Log.i("status", "about to delete event with id " + event.getId() +
                            " and name " + event.getName() + " from database.");
                    eventServices.deleteEventById(event.getId());



                }
                View parent = (View)v.getParent().getParent().getParent();
                parent.setVisibility(View.GONE);
            }
        });

        layout.addView(v);
    }

    public void switchToCreate(View view) {
        Intent addProfile = new Intent(this, profile.class);
        addProfile.putExtra(tabState, 1);
        addProfile.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        //recreate();
        startActivity(addProfile);
        overridePendingTransition(0, 0);
        finish();
    }

    public void switchToJoin(View view) {
        Intent addProfile = new Intent(this, profile.class);
        addProfile.putExtra(tabState, 2);
        addProfile.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(addProfile);
        overridePendingTransition(0, 0);

        finish();
        //recreate();
    }

    public void deleteEvent(View view){
        TextView textView = view.findViewById(R.id.profileDelete);

        if (state == 1) {
            eventServices.deleteEventById(Integer.parseInt(textView.getHint().toString()));
            switchToCreate(view);
        }else if (state == 0) {
            userServices.removeUserFromEvent(userId, Integer.parseInt(textView.getHint().toString()));

        }
    }

    public void removeCards(View view){
        LinearLayout layout = (LinearLayout) findViewById(R.id.cardList);
        //layout.child
    }

    public void transitionToAttendee(View view){
        Button button = (Button) view;
        Log.i("Denny2", String.valueOf(Integer.parseInt(button.getHint().toString())));
        Intent intent = new Intent(view.getContext(), AttendeeList.class);
        int eventID = Integer.parseInt(button.getHint().toString());
        intent.putExtra("eventID", eventID);
        startActivity(intent);

    }
}