package com.example.b07_project;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import com.example.b07_project.Venue;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Locale;

public class AddVenue extends AppCompatActivity {
    int numOfSports = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_venue);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void createVenue(View view)
    {
        com.example.b07_project.Venue venue = new com.example.b07_project.Venue();
        venue.name = ((EditText)findViewById(R.id.venueName)).getText().toString();
        Locale locale = new Locale("English", ((EditText)findViewById(R.id.venueCountry)).getText().toString());

        System.out.println("Done Setting Name");

        //set address
        Address address = new Address();
        address.streetAddress = ((EditText)findViewById(R.id.venueStreetAddress)).getText().toString();
        address.city = ((EditText)findViewById(R.id.venueCity)).getText().toString();
        address.country = ((EditText)findViewById(R.id.venueCountry)).getText().toString();
        address.state = ((EditText)findViewById(R.id.venueState)).getText().toString();
        address.postalCode = ((EditText)findViewById(R.id.venuePostalCode)).getText().toString();
        venue.address = address;
        System.out.println("Done Setting Address");

        //set max cap
        venue.capacity = Integer.parseInt(((EditText)findViewById(R.id.venueMaxCapacity)).getText().toString());
        System.out.println("Done Setting Max Cap");

        //Set availability
        //*Deprecated system
//        TimePicker pickedStartTime = (TimePicker)findViewById(R.id.venueStartTime);
//        GregorianCalendar startDate = new GregorianCalendar();
//        startDate.setTime(new Date());
//
//        TimePicker pickedFinishTime = (TimePicker)findViewById(R.id.venueEndTime);
//        GregorianCalendar endDate = new GregorianCalendar(pickedFinishDate.getYear(), pickedFinishDate.getYear(), pickedFinishDate.getDayOfMonth(),
//                pickedFinishTime.getHour(), pickedFinishTime.getMinute());
//        venue.availableFrom = startDate.getTimeInMillis();
//        System.out.println("Done Setting Availability");

        //Set Time
        TimePicker pickedStartTime = (TimePicker)findViewById(R.id.venueStartTime);
        Time startTime = new Time();
        startTime.setHour(pickedStartTime.getHour());
        startTime.setMin(pickedStartTime.getMinute());
        venue.availableFrom = startTime;

        TimePicker pickedEndTime = (TimePicker)findViewById(R.id.venueEndTime);
        Time endTime = new Time();
        endTime.setHour(pickedEndTime.getHour());
        endTime.setMin(pickedEndTime.getMinute());
        venue.availableTo = endTime;

        //Set days of the week available
        String daysAvailable = "";
        if(((CheckBox)findViewById(R.id.monday)).isChecked()) daysAvailable += "1";
        else daysAvailable += "0";
        if(((CheckBox)findViewById(R.id.tuesday)).isChecked()) daysAvailable += "1";
        else daysAvailable += "0";
        if(((CheckBox)findViewById(R.id.wednesday)).isChecked()) daysAvailable += "1";
        else daysAvailable += "0";
        if(((CheckBox)findViewById(R.id.thursday)).isChecked()) daysAvailable += "1";
        else daysAvailable += "0";
        if(((CheckBox)findViewById(R.id.friday)).isChecked()) daysAvailable += "1";
        else daysAvailable += "0";
        if(((CheckBox)findViewById(R.id.saturday)).isChecked()) daysAvailable += "1";
        else daysAvailable += "0";
        if(((CheckBox)findViewById(R.id.sunday)).isChecked()) daysAvailable += "1";
        else daysAvailable += "0";
        venue.daysAvailable = daysAvailable;

        //set description
        venue.description = ((EditText)findViewById(R.id.venueDiscription)).getText().toString();

        //sports
        venue.sports = new ArrayList<String>();
        LinearLayout layout = (LinearLayout)findViewById(R.id.sports);
        for(int i=0; i<layout.getChildCount(); i++)
        {
            EditText sport = (EditText)layout.getChildAt(i);
            venue.sports.add(sport.getText().toString());
        }

        //set max concurent events
        venue.maxConcurrentActivities = Integer.parseInt(((EditText)findViewById(R.id.maxActivities)).getText().toString());

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Venues");

        myRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            int maxId = -1;
            int tempId = 0;
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                for(DataSnapshot i : task.getResult().getChildren())
                {
                    tempId = i.getValue(Venue.class).id;
                    if(tempId > maxId) maxId = tempId;
                }
                venue.id = maxId + 1;
                myRef.child(venue.id + "").setValue(venue);
            }
        });

        Intent addVenue = new Intent(this, MainActivity.class);
        startActivity(addVenue);
        finish();
    }

    public void addSport(View view)
    {
        numOfSports += 1;
        LinearLayout layout = (LinearLayout)findViewById(R.id.sports);

        EditText newSport = new EditText(this);
        layout.getLayoutParams().height += 128;

        layout.addView(newSport);
    }
}