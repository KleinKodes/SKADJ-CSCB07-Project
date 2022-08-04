package com.example.b07_project;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
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

    public String getCurrentUserId() {return firebaseAuth.getCurrentUser().getUid();}
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
                Log.e("UserService Error", "could not find user by id");
            }
        }

        return user[0];


    }

    //END EXPERIMENTAL CODE BLOCK






    public void deleteUserFromDatabase(String userId){

    }

    public void removeUserFromEvent(String userId, int eventId){
        //two parts - delete event from user's joined events list, delete user from attendees list
//
//        userRef.child(userId).child("joinedEvents").child(eventId + "").removeValue();
//        eventRef.child(eventId + "").child("attendees").child(userId).removeValue();


        Log.i("status", "event id =" + eventId);
        Log.i("status", "AAAAAAA");
        DatabaseReference joinedEventsRef = userRef.child(userId).child("joinedEvents");
        joinedEventsRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                ArrayList<Long> joinedEvents = (ArrayList<Long>) task.getResult().getValue();
                if (joinedEvents == null) { Log.i("status", "joined events was null");return;}
                //if (!joinedEvents.contains((Integer)eventId)) { Log.i("status", "joined events did not have correct event id..." + joinedEvents.toString());return;}
                Boolean flag = false;
                for (Long i : joinedEvents){
                    if (i == eventId) flag = true;
                }
                if (!flag) return;

                joinedEvents.remove(joinedEvents.indexOf((long)eventId));

                DatabaseReference attendeesRef = eventRef.child(eventId + "").child("attendees");

                Log.i("status", "AAAAAAA");

                attendeesRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        ArrayList<String> attendees = (ArrayList<String>) task.getResult().getValue();
                        if (attendees == null) return;
                        if (!attendees.contains(userId)) return;
                        DatabaseReference attendeeNumRef= eventRef.child(eventId + "").child("attendeeNum");
                        Log.i("status", "AAAAAAA");
                        attendeeNumRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                int attendeeNum = task.getResult().getValue(int.class);
                                attendeeNum -= 1;
                                attendees.remove(attendees.indexOf(userId));
                                joinedEventsRef.setValue(joinedEvents);
                                attendeesRef.setValue(attendees);
                                attendeeNumRef.setValue(attendeeNum);
                                Log.i("status", "AAAAAAA");
                            }
                        });

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

                joinedEventsRef.child(eventId + "").setValue(eventId);


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

        currentUser.id = getCurrentUserId();


                DatabaseReference attendeesRef = eventRef.child(eventId + "").child("attendees");
                attendeesRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        ArrayList<String> attendees = (ArrayList<String>) task.getResult().getValue();
                        if (attendees == null) return;
                        if (attendees.contains(currentUser.id)) return;
                        attendees.add(currentUser.id);

                        DatabaseReference joinedEventsRef = userRef.child(currentUser.getId()).child("joinedEvents");

                       joinedEventsRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                ArrayList<Integer> joinedEvents = (ArrayList<Integer>) task.getResult().getValue();
                                joinedEvents.add(eventId);

                                attendeesRef.setValue(attendees);

                                joinedEventsRef.setValue(joinedEvents);

                            }
                        });






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

    public void logInUser(String email, String password, View view, Activity activity){
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {


                    currentUser = findUserByUserId(task.getResult().getUser().getUid());
                    activity.finish();

                } else {
                    Log.i("Login", "LOGIN FAILED NOOO");

                    Snackbar mySnackbar = Snackbar.make(view, "Invalid email/password combination.", BaseTransientBottomBar.LENGTH_SHORT);
                    mySnackbar.show();
                }

            }
        });

    }

    public void signUpUser(String email, String password, View view){
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

    public void routeUser(Context context) {

//        currentUser = findUserByUserId(userId);
//        while (currentUser.getEmail() == null){
//            try {
//                wait(1000);
//            }catch(Exception e){
//                Log.e("UserService Error", "could not find user");
//            }
//        }
//
//        Log.i("userInfo", "email:" + currentUser.getEmail() + " auth:" + currentUser.getAuth());


        Log.i("Current user", firebaseAuth.getCurrentUser().getUid());
        userRef.child(firebaseAuth.getCurrentUser().getUid()).child("auth").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.getResult().getValue() == null){
                    Intent loginIntent = new Intent(context, LoginActivity.class);
                    loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    context.startActivity(loginIntent);
                }
                int auth = task.getResult().getValue(int.class);

                if (auth == 1){
                    Intent adminIntent = new Intent(context, AdminActivity.class);
                    adminIntent.putExtra("auth", currentUser.getAuth());
                    adminIntent.putExtra("firstName", currentUser.getFirstName());
                    adminIntent.putExtra("userId", currentUser.getId());
                    adminIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    context.startActivity(adminIntent);




                }else if (auth == 0){
                    Intent customerIntent = new Intent(context, VenuePageDennt.class);
                    customerIntent.putExtra("auth", currentUser.getAuth());
                    customerIntent.putExtra("firstName", currentUser.getFirstName());
                    customerIntent.putExtra("userId", currentUser.getId());
                    customerIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    context.startActivity(customerIntent);

                } else{
                    Log.e("LoginIssue", "user does not have correct auth value");
                }
            }
        });

//        if (currentUser.getAuth() == 1){
//            Intent adminIntent = new Intent(context, AdminActivity.class);
//            adminIntent.putExtra("auth", currentUser.getAuth());
//            adminIntent.putExtra("firstName", currentUser.getFirstName());
//            adminIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            context.startActivity(adminIntent);
//
//
//
//
//        }else if (currentUser.getAuth() == 0){
//            Intent customerIntent = new Intent(context, VenuePageDennt.class);
//            customerIntent.putExtra("auth", currentUser.getAuth());
//            customerIntent.putExtra("firstName", currentUser.getFirstName());
//
//            customerIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            context.startActivity(customerIntent);
//
//        } else{
//            Log.e("LoginIssue", "user does not have correct auth value");
//        }


        return;
    }

}
