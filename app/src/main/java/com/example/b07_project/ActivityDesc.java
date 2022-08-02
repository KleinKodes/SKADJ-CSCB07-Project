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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ActivityDesc extends AppCompatActivity {
    String[] activityInfo;
    int eventId;
    String userId;
    Event event;
    Boolean isThisMyEvent;
    Boolean mode;
    String firstName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desc);
        Intent intent = getIntent();
        activityInfo= intent.getStringArrayExtra(activityPageDenny.activity);
        eventId = intent.getIntExtra("eventId", -1);
        userId = intent.getStringExtra("userId");
        isThisMyEvent = intent.getBooleanExtra("isThisMyEvent", false);

        mode = intent.getBooleanExtra("approvalNeeded", false);
        Log.i("mode", mode.toString());

        if (userId == null) userId = "";
        firstName = intent.getStringExtra("firstName");
        if(firstName != null){
            TextView textView = findViewById(R.id.profileUserName);
            textView.setText(firstName);
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference eventRef = database.getReference("Events");
        DatabaseReference userRef = database.getReference("Users");

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

        ViewGroup infoContainer = (ViewGroup) findViewById(R.id.infoContainer);
        TextView host = (TextView)infoContainer.getChildAt(0);
        TextView start = (TextView)infoContainer.getChildAt(1);
        TextView end = (TextView)infoContainer.getChildAt(2);
        TextView date = (TextView)infoContainer.getChildAt(3);
        TextView activity = (TextView) findViewById(R.id.venueDescVenue);

        host.setText(activityInfo[4]);
        start.setText(activityInfo[1]);
        end.setText(activityInfo[2]);
        date.setText(activityInfo[0]);
        activity.setText(activityInfo[3]);
    }

    public void backToActivities(View view){
        finish();
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
                    intent.putExtra("userId", userId);
                    intent.putExtra("firstName", firstName);
                    startActivity(intent);
                    finish();
                    return;

                }
                if (event.attendees == null) event.attendees = new ArrayList<String>();
                if (event.attendees.contains(userId)){


                    //TODO: Remove the event from the user's joined events list
                    userRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            ArrayList<Integer> joinedEvents = (ArrayList<Integer>) task.getResult().getValue();
                            if (joinedEvents!= null) joinedEvents.remove(joinedEvents.indexOf(eventId));

                            userRef.setValue(joinedEvents);
                            event.attendees.remove(event.attendees.indexOf(userId));
                            eventRef.child(eventId + "").setValue(event);

                            Intent intent = new Intent(getBaseContext(), activityPageDenny.class);
                            startActivity(intent);
                            finish();

                        }
                    });


                }else {


                    //TODO: Add the event to the user's joined events list
                    userRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            ArrayList<Integer> joinedEvents = (ArrayList<Integer>) task.getResult().getValue();
                            if (joinedEvents== null) joinedEvents = new ArrayList<Integer>();

                            joinedEvents.add(eventId);

                            userRef.setValue(joinedEvents);
                            event.attendees.add(userId);
                            eventRef.child(eventId + "").setValue(event);

                            Intent intent = new Intent(getBaseContext(), activityPageDenny.class);
                            startActivity(intent);
                            finish();

                        }
                    });
                }

            }
        });

    }
}