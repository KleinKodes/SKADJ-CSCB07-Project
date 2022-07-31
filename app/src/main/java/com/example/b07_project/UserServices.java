package com.example.b07_project;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class UserServices {
    FirebaseDatabase database;
    DatabaseReference eventRef;
    DatabaseReference userRef;
    DatabaseReference venueRef;
    public UserServices(){
        this.database = FirebaseDatabase.getInstance();
        this.eventRef = database.getReference("Events");
        this.userRef = database.getReference("Users");
        this.venueRef = database.getReference("Venues");
    }



    public void deleteUserFromDatabase(String userId){

    }

    public void removeUserFromEvent(String userId, int eventId){
        //two parts - delete event from user's joined events list, delete user from attendees list

        userRef.child(userId).child("joinedEvents").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                ArrayList<Integer> joinedEvents = (ArrayList<Integer>) task.getResult().getValue();
                if (joinedEvents == null) return;
                if (!joinedEvents.contains(eventId)) return;
                joinedEvents.remove(joinedEvents.indexOf(eventId));

                eventRef.child(eventId + "").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        ArrayList<String> attendees = (ArrayList<String>) task.getResult().getValue();
                        if (attendees == null) return;
                        if (!attendees.contains(userId)) return;
                        attendees.remove(attendees.indexOf(userId));
                    }
                });
            }
        });
    }

    public void addUserToEvent(String userId, int eventId){

    }

    public void checkIfUserHasJoinedEvent(String userId, int eventId){

    }

    public void checkIfUserOwnsEvent(String userId, int eventId){

    }

}
