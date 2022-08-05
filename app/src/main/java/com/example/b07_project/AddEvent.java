package com.example.b07_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.time.Instant;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class AddEvent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event_denny);

        Intent intent = getIntent();

        TextView sportText = findViewById(R.id.textView100);
        sportText.setText(intent.getStringExtra("sport"));

        TextView venueText = findViewById(R.id.venueName);
        venueText.setText(intent.getStringExtra("venue"));


        final ArrayList<String> sports = new ArrayList<>();
        sports.add("Volleyball");
        sports.add("Quiddich");
        sports.add("Not league");
        sports.add("extreme seven eating");



    }

    public void createEvent(View view)
    {
        System.out.println("someone clicked the button");
        final int[] curMaxId = {-1}; //java made me use a final length 1 array instead of an integer idk y



        EditText editText = (EditText) findViewById(R.id.eventName);
        String eventName = editText.getText().toString();
        editText = (EditText) findViewById(R.id.eventCapacity);
        Event event = new Event(eventName);
        event.capacity = Integer.parseInt(editText.getText().toString());
        editText= (EditText) findViewById(R.id.eventDescription);
        event.eventDescription= editText.getText().toString();
        TimePicker timePicker = (TimePicker) findViewById(R.id.eventStartTime);

        DatePicker datePicker = (DatePicker) findViewById(R.id.eventDate);

        event.setStartDate(new GregorianCalendar(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), timePicker.getCurrentHour(), timePicker.getCurrentMinute()));
        timePicker = (TimePicker) findViewById(R.id.eventEndTime);
        event.setEndDate(new GregorianCalendar(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), timePicker.getCurrentHour(), timePicker.getCurrentMinute()));
        event.sport = getIntent().getStringExtra("sport");
        event.venueId = getIntent().getIntExtra("venueId", -1);
        event.ownerId = getIntent().getStringExtra("userID");


        // info should all be inputted by this point, code checks validity here
        if (!validateEvent(view, event)) return;


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Events");
        Log.i("demo", "honestly idk");




        myRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            int tempId;
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                System.out.println("why do we exist? just to suffer?");
                if (!task.isSuccessful()) {
                    Log.e("demo", "Error getting data", task.getException());
                }
                else {
                    for (DataSnapshot childSnapshot : task.getResult().getChildren()) {
                        tempId = childSnapshot.getValue(Event.class).id;
                        Log.i("demo", "current max id: " + curMaxId[0]);
                        if (tempId >= curMaxId[0]) curMaxId[0] = tempId;
                        Log.i("demo", "id = " + tempId);
                        Log.i("demo", "new current max id: " + curMaxId[0]);
//
//
//                    tempId = task.getResult().getValue();
//                    Log.i("demo", "id = " + tempId);
//                    if (tempId >= curMaxId[0]) curMaxId[0] = tempId;

                    }
                    //Log.i("demo", task.getResult().getValue().toString());

                    event.id=curMaxId[0]+1;
                //event.ownerId = 0; //temporary until we make user class


                myRef.child(event.id + "").setValue(event);

                DatabaseReference venueRef = database.getReference("Venues");
                venueRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        for (DataSnapshot childSnapShot : task.getResult().getChildren()){
                            Venue venue = childSnapShot.getValue(Venue.class);
                            if (venue.getId() == event.getVenueId()) {
                                venue.scheduledEvents.add(event.getId());
                                //adds event id to venue's list of scheduled event id's
                                venueRef.child(venue.getId() + "").setValue(venue);
                                //updates said venue in database
                            }

                        }
                    }
                });



                }
            }
        });



        Intent intent = new Intent(this, MainActivityDeprecated.class);
        startActivity(intent);
        finish();

    }


/*    private void makePopUp(View view, String message) {
        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_window, null);


        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            popupWindow.setElevation(20);
        }

        TextView textView = (TextView) popupView.findViewById(R.id.popup_text);

        textView.setText(message);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }*/

    private boolean validateEvent(View view, Event event) { // check capacity for events
        Log.i("status", "validating event");
        if (event.getStartTimeStamp() < Instant.now().toEpochMilli()) {
            //makePopUp(view, "Invalid start date");
            Snackbar mySnackbar = Snackbar.make(view, "Invalid start date", BaseTransientBottomBar.LENGTH_SHORT);
            mySnackbar.show();
            return false;
        }

        if (event.getEndTimeStamp() < event.getStartTimeStamp()) {
            //makePopUp(view, "Invalid end date");
            Snackbar mySnackbar = Snackbar.make(view, "Invalid end date", BaseTransientBottomBar.LENGTH_SHORT);
            mySnackbar.show();
            return false;
        }

        if (event.getCapacity() < 10){
            //makePopUp(view, "Capacity too low");
            Log.i("status", "capacity low, should give popup");
            Snackbar mySnackbar = Snackbar.make(view, "Capacity too low", BaseTransientBottomBar.LENGTH_SHORT);
            mySnackbar.show();
            return false;
        }

        if (event.getName().trim().isEmpty() || event.getName().trim() == "Name"){
            //makePopUp(view, "Please give your event a name");
            Snackbar mySnackbar = Snackbar.make(view, "Please give your event a name", BaseTransientBottomBar.LENGTH_SHORT);
            mySnackbar.show();
            return false;
        }
        if (event.getEventDescription().length() < 20){
            //makePopUp(view, "Please give your event a good description.");
            Snackbar mySnackbar = Snackbar.make(view, "Please give your event a good description", BaseTransientBottomBar.LENGTH_SHORT);
            mySnackbar.show();
            return false;
        }


        return true;


    }
}