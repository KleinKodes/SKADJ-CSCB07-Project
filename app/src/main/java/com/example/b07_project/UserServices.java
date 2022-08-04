package com.example.b07_project;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class UserServices {
    FirebaseDatabase database;
    DatabaseReference eventRef;
    DatabaseReference userRef;
    DatabaseReference venueRef;
    FirebaseAuth firebaseAuth;
    //EventServices eventServices;

    public User currentUser;

    public UserServices(){
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.database = FirebaseDatabase.getInstance();
        this.eventRef = database.getReference("Events");
        this.userRef = database.getReference("Users");
        this.venueRef = database.getReference("Venues");
        this.currentUser = new User();
        //this.eventServices = new EventServices();
        if (firebaseAuth.getCurrentUser() != null) {
            userRef.child(firebaseAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    currentUser = task.getResult().getValue(User.class);
                }
            });
        }

    }

    public String getCurrentUserId() {return currentUser.id;}
    public String getCurrentUserName(){return currentUser.firstName;}
    public User getCurrentUser(){return currentUser;}
    public int getCurrentUserAuth(){return currentUser.auth;}

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public ArrayList<Integer> getCurrentUserJoinedEventIds() {return currentUser.joinedEvents;}
    public ArrayList<Integer> getCurrentUserCreatedEventIds() {return currentUser.createdEvents;}


    //EXPERIMENTAL CODE - AVOID USING WHENEVER POSSIBLE

    //it's so funny that i accidentally used this and it really works so far
    public User findUserByUserId(String userId) {
        final User[] user = {new User()};

        final Boolean[] notDone = {true};
        userRef.child(userId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                user[0] = task.getResult().getValue(User.class);
                notDone[0] = false;

            }


        });

        while (user[0] == null && notDone[0]){
            try {
                wait(10);
            }catch(Exception e){
                Log.e("UserService Error", "could not find user");
            }
        }

        return user[0];


    }

    //END EXPERIMENTAL CODE BLOCK






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

        DatabaseReference joinedEventsRef = userRef.child(userId).child("joinedEvents");
        joinedEventsRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                ArrayList<Integer> joinedEvents = (ArrayList<Integer>) task.getResult().getValue();
                if (joinedEvents == null) joinedEvents = new ArrayList<Integer>();
                if (joinedEvents.contains(userId)) return;
                joinedEvents.add(eventId);
                joinedEventsRef.setValue(joinedEvents);


                DatabaseReference attendeeRef = eventRef.child(eventId + "").child("attendees");
                        attendeeRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        ArrayList<String> attendees = (ArrayList<String>) task.getResult().getValue();
                        if (attendees == null) return;
                        if (attendees.contains(userId)) return;
                        attendees.add(userId);
                        attendeeRef.setValue(attendees);
                    }
                });
            }
        });

    }

    public void addCurrentUserToEvent(int eventId){

                DatabaseReference attendeesRef = eventRef.child(eventId + "").child("attendees");
                attendeesRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        ArrayList<String> attendees = (ArrayList<String>) task.getResult().getValue();
                        if (attendees == null) return;
                        if (attendees.contains(currentUser.id)) return;
                        attendees.add(currentUser.id);
                        attendeesRef.setValue(attendees);
                        currentUser.joinedEvents.add(eventId);
                        userRef.child(currentUser.id).child("joinedEvents").setValue(currentUser.joinedEvents);
                    }
                });


    }

    public Boolean checkIfUserHasJoinedEvent(String userId, int eventId){

        return findUserByUserId(userId).joinedEvents.contains(eventId);
    }

    public Boolean checkIfUserOwnsEvent(String userId, int eventId){

        return findUserByUserId(userId).createdEvents.contains(eventId);

    }

    public void logOutCurrentUser(){
        firebaseAuth.signOut();
    }

    public void logInUser(String email, String password, View view){
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {


                    currentUser = findUserByUserId(task.getResult().getUser().getUid());

                } else {
                    Log.i("Login", "LOGIN FAILED NOOO");

                    Snackbar mySnackbar = Snackbar.make(view, "Invalid email/password combination.", BaseTransientBottomBar.LENGTH_SHORT);
                    mySnackbar.show();
                }

            }
        });

    }

}
