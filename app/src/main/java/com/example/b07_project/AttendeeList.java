package com.example.b07_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AttendeeList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendee_list);
        //attendeeList
        Intent intent = getIntent();
        FirebaseDatabase database;
        DatabaseReference eventRef;
        DatabaseReference userRef;
        database = FirebaseDatabase.getInstance();
        eventRef = database.getReference("Events");
        userRef = database.getReference("Users");
        UserServices userServices;

        userServices = new UserServices();
        ((TextView)findViewById(R.id.profileUserName)).setText(userServices.getCurrentUserName());

        int eventId = intent.getIntExtra("eventID",1);
        Log.i("Denny", String.valueOf(eventId));
        eventRef.child(eventId + "").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                ViewGroup attendeeList = (ViewGroup) findViewById(R.id.attendeeList);
                Event event = task.getResult().getValue(Event.class);
                if(task.getResult().getValue(Event.class).attendees == null){
                    View v = LayoutInflater.from(attendeeList.getContext()).inflate(R.layout.attendee_card, null);
                    TextView textView = (TextView) v.findViewById(R.id.attendeeCardText);
                    textView.setText("No Attendees Currently");
                    attendeeList.addView(v);
                }
                else {
                    for (String userId : task.getResult().getValue(Event.class).attendees) {
                        userRef.child(userId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                View v = LayoutInflater.from(attendeeList.getContext()).inflate(R.layout.attendee_card, null);
                                TextView textView = (TextView) v.findViewById(R.id.attendeeCardText);
                                textView.setText(task.getResult().getValue(User.class).getFullName());
                                attendeeList.addView(v);
                            }
                        });
                    }
                }

            }
        });
    }

    public void goBackToPage(View view){
        super.onBackPressed();
        finish();
    }
}