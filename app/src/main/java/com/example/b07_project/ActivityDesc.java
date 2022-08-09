package com.example.b07_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ActivityDesc extends AppCompatActivity {
    DatabaseReference eventRef;
    DatabaseReference venueRef;
    DatabaseReference userRef;
    String[] activityInfo;
    int eventId;
    String userId;
    Event event;
    Venue venue;
    Boolean isThisMyEvent;
    Boolean mode;
    String firstName;
    int auth;
    UserServices userServices;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desc);
        Intent intent = getIntent();
        userServices = new UserServices();
        activityInfo= intent.getStringArrayExtra(activityPageDenny.activity);
        eventId = intent.getIntExtra("eventId", -1);
        userId = userServices.getCurrentUserId();
        isThisMyEvent = intent.getBooleanExtra("isThisMyEvent", false);
        auth = userServices.getCurrentUserAuth();

        Log.i("Status", "UserInfo: " + userServices.getCurrentUser());

        View home = findViewById(R.id.homeButton);
        home.setOnClickListener(new Navigation());
        View profile = findViewById(R.id.profileButton);
        profile.setOnClickListener(new Navigation());
        View logout = findViewById(R.id.logOutButton);
        logout.setOnClickListener(new Navigation());

        ((TextView)findViewById(R.id.profileUserName)).setText(userServices.getCurrentUserName());

        mode = intent.getBooleanExtra("approvalNeeded", false);
        Log.i("mode", mode.toString());


        if(auth == 1)
        {
            ((TextView)findViewById(R.id.joinEventButton)).setText("Accept");
            View parent = (View)findViewById(R.id.homeButton).getParent();
            parent.setVisibility(View.GONE);
        }

        if (userId == null) userId = "";
        firstName = userServices.getCurrentUserName();
        if(firstName != null){
            TextView textView = findViewById(R.id.profileUserName);
            textView.setText(firstName);
        }

        if (mode){
             View navView =(View) findViewById(R.id.logOutButton).getParent();
             navView.setVisibility(View.GONE);
             View denyView = (View) findViewById(R.id.denyEventButton);
             denyView.setVisibility(View.VISIBLE);
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        eventRef = database.getReference("Events");
        userRef = database.getReference("Users");

        eventRef.child(eventId + "").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                Button button = findViewById(R.id.joinEventButton);
                event = task.getResult().getValue(Event.class);
                if (mode){
                    button.setText("Approve");

                }else
                if (isThisMyEvent){

                    button.setVisibility(View.GONE);
                    //button.setClickable(false);

                }else
                if (event.attendees != null && event.attendees.contains(userId)){
                    button.setText("Exit");
                    //button.setClickable(false);

                }

            }
        });


        initializevalues();

    }

    public void initializevalues(){
        Log.i("Jacky", "EventID:" + eventId);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        eventRef = database.getReference("Events");
        eventRef.child(eventId + "").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                event = task.getResult().getValue(Event.class);

                venueRef = database.getReference("Venues");
                venueRef.child(event.getVenueId() + "").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                     @Override
                     public void onComplete(@NonNull Task<DataSnapshot> task) {
                            venue = task.getResult().getValue(Venue.class);
                            TextView venueName = (TextView) findViewById(R.id.activityDescVenue);
                            venueName.setText(venue.getName());
                       }
                     });
                ViewGroup infoContainer = (ViewGroup) findViewById(R.id.infoContainer);
                TextView host = (TextView)infoContainer.getChildAt(1);
                TextView start = (TextView)infoContainer.getChildAt(2);
                TextView startEnd = (TextView)infoContainer.getChildAt(3);
                TextView date = (TextView)infoContainer.getChildAt(4);
                TextView activity = (TextView) findViewById(R.id.venueDescVenue);
                TextView endDate = (TextView) findViewById(R.id.activityDescEndDate);
                TextView sports = (TextView) findViewById(R.id.activityDescSports);
                TextView desc = (TextView) findViewById(R.id.activityDescDescription);
                TextView cap = (TextView) findViewById(R.id.activityDescCapacity);
                TextView venueName = (TextView) findViewById(R.id.activityDescVenue);

                host.setText(event.getOwnerId());
                start.setText("Start Time: "+event.getStartTimeString());
                startEnd.setText("End Time: "+event.getEndTimeString());
                date.setText("Start Date: "+event.getStartDateString());
                endDate.setText("End Date: "+event.getEndDateString());
                sports.setText("Sport: "+event.getSport());
                cap.setText(event.getAttendeeNum()+"/"+event.getCapacity());
                desc.setText(event.getEventDescription());
                activity.setText(event.getName());

            }
        });

    }

    public void joinEvent(View view){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference eventRef = database.getReference("Events");
        DatabaseReference userRef = database.getReference("Users").child(userId).child("joinedEvents");



        eventRef.child(eventId + "").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                event = task.getResult().getValue(Event.class);
                if (mode){
                    event.approved = true;
                    eventRef.child(eventId + "").setValue(event);
                    Intent intent = new Intent(getBaseContext(), activityPageDenny.class);
                    intent.putExtra("approvalNeeded", mode);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                    return;

                }
                if (event.attendees == null) event.attendees = new ArrayList<String>();
                if (event.attendees.contains(userId)){
                    userServices.removeUserFromEvent(userId, eventId);
                }else {
                    userServices.addCurrentUserToEvent(eventId);
//
//
                }

                Intent intent = new Intent(getBaseContext(), activityPageDenny.class);
                intent.putExtra("approvalNeeded", mode);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);
                finish();

            }
        });




    }

    public void DenyEvent(View view){
        FirebaseDatabase.getInstance().getReference("Events").child(eventId + "").removeValue();
        Intent intent = new Intent(getBaseContext(), activityPageDenny.class);
        intent.putExtra("approvalNeeded", mode);
        startActivity(intent);
        finish();
    }

    public void backToActivityPage(View view){
        super.onBackPressed();
        finish();
    }
}