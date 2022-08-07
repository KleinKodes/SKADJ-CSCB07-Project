package com.example.b07_project;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class AddVenue extends AppCompatActivity {
    int numOfSports = 0;
    int auth = 0;
    int mode = 0;
    int venueID = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_venue_denny);

        UserServices userServices = new UserServices();
        auth = userServices.getCurrentUserAuth();
        mode = this.getIntent().getIntExtra("mode", 0);

        venueID = this.getIntent().getIntExtra("venueId",-1);
        ((Button)findViewById(R.id.deleteButton)).setVisibility(View.INVISIBLE);


        if(mode == 1){
            TextView modeText = ((TextView)findViewById(R.id.modeText));
            modeText.setText("View/Edit Venue");
            ((Button)findViewById(R.id.deleteButton)).setVisibility(View.VISIBLE);

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("Venues").child(this.getIntent().getIntExtra("venueId",-1)+"");

            myRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    Venue venue = ((Venue)task.getResult().getValue(Venue.class));
                    ((EditText)findViewById(R.id.venueName)).setText(venue.name);

                    ((EditText)findViewById(R.id.venueStreetAddress)).setText(venue.address.streetAddress);
                    ((EditText)findViewById(R.id.venueCity)).setText(venue.address.city);
                    ((EditText)findViewById(R.id.venueCountry)).setText(venue.address.country);
                    ((EditText)findViewById(R.id.venueState)).setText(venue.address.state);
                    ((EditText)findViewById(R.id.venuePostalCode)).setText(venue.address.postalCode);

                    ((EditText)findViewById(R.id.venueMaxCapacity)).setText(venue.capacity + "");

                    ((TimePicker)findViewById(R.id.venueStartTime)).setHour(venue.availableFrom.getHour());
                    ((TimePicker)findViewById(R.id.venueStartTime)).setMinute(venue.availableFrom.getMin());

                    ((TimePicker)findViewById(R.id.venueEndTime)).setHour(venue.availableTo.getHour());
                    ((TimePicker)findViewById(R.id.venueEndTime)).setMinute(venue.availableTo.getMin());

                    String[] weeks = venue.daysAvailable.split("");
                    if(weeks[0].equals("1")) ((CheckBox)findViewById(R.id.monday)).setChecked(true);
                    if(weeks[1].equals("1")) ((CheckBox)findViewById(R.id.tuesday)).setChecked(true);
                    if(weeks[2].equals("1")) ((CheckBox)findViewById(R.id.wednesday)).setChecked(true);
                    if(weeks[3].equals("1")) ((CheckBox)findViewById(R.id.thursday)).setChecked(true);
                    if(weeks[4].equals("1")) ((CheckBox)findViewById(R.id.friday)).setChecked(true);
                    if(weeks[5].equals("1")) ((CheckBox)findViewById(R.id.saturday)).setChecked(true);
                    if(weeks[6].equals("1")) ((CheckBox)findViewById(R.id.sunday)).setChecked(true);

                    ((EditText)findViewById(R.id.venueDiscription)).setText(venue.description);

                    ArrayList<String> sports = venue.sports;
                    ((EditText)findViewById(R.id.sport)).setText(sports.get(0));
                    for(int i=1; i<sports.size(); i++)
                    {
                        addSport(sports.get(i));
                    }

                    ((EditText)findViewById(R.id.maxActivities)).setText(venue.maxConcurrentActivities +"");
                }
            });
        }
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
        if(((EditText) findViewById(R.id.venueMaxCapacity)).getText().toString().equals("")){
            venue.capacity = 0;
        }
        else {
            venue.capacity = Integer.parseInt(((EditText) findViewById(R.id.venueMaxCapacity)).getText().toString());
        }
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

        //initialze empty scheduled events list
        venue.scheduledEvents = new ArrayList<Integer>();

        //sports
        venue.sports = new ArrayList<String>();
        LinearLayout layout = (LinearLayout)findViewById(R.id.sports);
        venue.sports.add(((EditText)layout.findViewById(R.id.sport)).getText().toString());
        for(int i=1; i<layout.getChildCount(); i++)
        {
            View v = layout.getChildAt(i);
            EditText sport = (EditText)v.findViewById(R.id.sportName);
            venue.sports.add(sport.getText().toString());
        }

        //set max concurrent events
        if(((EditText) findViewById(R.id.maxActivities)).getText().toString().equals("")){
            venue.maxConcurrentActivities = 0;
        }
        else {
            venue.maxConcurrentActivities = Integer.parseInt(((EditText) findViewById(R.id.maxActivities)).getText().toString());
        }
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Venues");

        // info should all be inputted by this point, code checks validity here
        if(!validateData(view, venue)){return;}

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
                if(auth == 1 && mode == 1)
                {
                    int id = getIntent().getIntExtra("venueId",0);
                    venue.id = id;
                    myRef.child(getIntent().getIntExtra("venueId",0) + "").setValue(venue);

                }
                else myRef.child(venue.id + "").setValue(venue);
            }
        });


        Intent addVenue = new Intent(this, AdminActivity.class);
        startActivity(addVenue);
        finish();
    }

    public void deleteVenue(View view)
    {
        Intent chooseVenue = new Intent(this, ChooseVenue.class);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Events");
        ref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                for(DataSnapshot i : task.getResult().getChildren())
                {
                    System.out.println("NOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
                    Event event = (Event)i.getValue(Event.class);
                    if(event.venueId == venueID){
                        Snackbar mySnackbar = Snackbar.make(view, "There are still Events at this Venue!", BaseTransientBottomBar.LENGTH_SHORT);
                        mySnackbar.show();
                        return;
                    }
                }
                DatabaseReference myRef = database.getReference("Venues").child(venueID + "");

                myRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        myRef.removeValue();

                        startActivity(chooseVenue);
                        finish();

                    }
                });


            }
        });

    }

    public void addSport(View view)
    {
        numOfSports += 1;
        LinearLayout layout = (LinearLayout)findViewById(R.id.sports);
        View v = LayoutInflater.from(this).inflate(R.layout.sport_card, null);
        ((Button)v.findViewById(R.id.minus)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout.removeView(v);
            }
        });


        layout.addView(v);
    }

    public void addSport(String text)
    {
        numOfSports += 1;
        LinearLayout layout = (LinearLayout)findViewById(R.id.sports);
        View v = LayoutInflater.from(this).inflate(R.layout.sport_card, null);
        ((EditText)(v.findViewById(R.id.sportName))).setText(text);
        ((Button)v.findViewById(R.id.minus)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout.removeView(v);
            }
        });


        layout.addView(v);
    }

/*    private void makePopUp(View view, String message){
        // inflate layout of popup window
        LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_window, null, false);
        // popup window creation
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;
        PopupWindow popupWindow = new PopupWindow(view, width, height, focusable);
        //showing popup window
//        if(popupWindow. != null) {
//            ((ViewGroup)popupWindow.getParent()).removeView(popupWindow); // <- fix
//        }

        if (popupView.getParent() != null){
            ((ViewGroup) popupView.getParent()).removeView(popupView);
        }
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            popupWindow.setElevation(20);
        }
        //Create textview for popup
        TextView textView = (TextView) popupView.findViewById(R.id.popup_text);
        textView.setText(message);
        // dismiss message when clicked
        popupView.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                popupWindow.dismiss();
                return true;
            }
        });
    }*/

    private boolean validateData(View view, Venue venue){

        //Check if venue has a name
        if(venue.getName().trim().isEmpty() || Objects.equals(venue.getName().trim(), "Venue Name")){
            //makePopUp(view, "Invalid venue name.");
            Snackbar mySnackbar = Snackbar.make(view, "Invalid venue name", BaseTransientBottomBar.LENGTH_SHORT);
            mySnackbar.show();
            return false;
        }
        //Check if venue has a proper address
        if(venue.address.city.trim().isEmpty() || venue.address.streetAddress.trim().isEmpty() ||
        venue.address.country.trim().isEmpty() || venue.address.postalCode.trim().isEmpty() || venue.address.state.trim().isEmpty()){
            //makePopUp(view, "Invalid venue address");
            Snackbar mySnackbar = Snackbar.make(view, "Invalid venue address", BaseTransientBottomBar.LENGTH_SHORT);
            mySnackbar.show();
            return false;
        }
        //Check if venue has any available times
        if(Objects.equals(venue.daysAvailable, "0000000")){
            //makePopUp(view, "Venue has to be available in at least one day");
            Snackbar mySnackbar = Snackbar.make(view, "Venue has to be available in at least one day", BaseTransientBottomBar.LENGTH_SHORT);
            mySnackbar.show();
            return false;
        }
        //Check if venue has a description
        if(venue.description.trim().isEmpty()){
            //makePopUp(view, "Give your venue a description");
            Snackbar mySnackbar = Snackbar.make(view, "Give your venue a description", BaseTransientBottomBar.LENGTH_SHORT);
            mySnackbar.show();
            return false;
        }
        //Check if venue has any sports, if the number of sports exceeds the max event amount, or if the max amount is empty
        if(venue.sports.size() == 0 || venue.maxConcurrentActivities == 0){
            //makePopUp(view, "Number of sports/maximum sports size invalid.");
            Snackbar mySnackbar = Snackbar.make(view, "Number of sports/maximum sports size invalid", BaseTransientBottomBar.LENGTH_SHORT);
            mySnackbar.show();
            return false;
        }
        // check for valid capacity value
        if(venue.capacity <= 0){
            //makePopUp(view, "Venue capacity empty.");
            Snackbar mySnackbar = Snackbar.make(view, "Venue capacity empty", BaseTransientBottomBar.LENGTH_SHORT);
            mySnackbar.show();
            return false;
        }
        //check time for venue
        if(venue.availableTo.getHour() < venue.availableFrom.getHour()){
            //makePopUp(view, "Invalid time");
            Snackbar mySnackbar = Snackbar.make(view, "Invalid time", BaseTransientBottomBar.LENGTH_SHORT);
            mySnackbar.show();
            return false;
        }
        else if(venue.availableTo.getHour() == venue.availableFrom.getHour())
        {
            if(venue.availableTo.getMin() < venue.availableFrom.getMin()){
                //makePopUp(view, "Invalid time");
                Snackbar mySnackbar = Snackbar.make(view, "Invalid time", BaseTransientBottomBar.LENGTH_SHORT);
                mySnackbar.show();
                return false;
            }
        }
        // check if the scheduled events are empty (no one should be able to create a venue and simultaneously create an event)
        if(venue.scheduledEvents.size() > 0){
            //makePopUp(view, "You time traveling bro????");
            Snackbar mySnackbar = Snackbar.make(view, "TIME LOOP", BaseTransientBottomBar.LENGTH_SHORT);
            mySnackbar.show();
            return false;
        }
        return true;

    }
}