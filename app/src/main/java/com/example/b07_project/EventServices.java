package com.example.b07_project;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EventServices {

    FirebaseDatabase database;
    DatabaseReference eventRef;
    DatabaseReference userRef;
    DatabaseReference venueRef;
    public EventServices(){
        this.database = FirebaseDatabase.getInstance();
        this.eventRef = database.getReference("Events");
        this.userRef = database.getReference("Users");
        this.venueRef = database.getReference("Venues");
    }

    public void deleteEventById(int eventId){

        UserServices userServices = new UserServices();

        eventRef.child(eventId + "").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                Event event = task.getResult().getValue(Event.class);
                if (event == null) return;
                if (event.attendees != null) {
                    for (String i : event.attendees) {
                        userServices.removeUserFromEvent(i, eventId);
                    }
                }
                eventRef.child(eventId + "").removeValue();
            }
        });


    }

}
