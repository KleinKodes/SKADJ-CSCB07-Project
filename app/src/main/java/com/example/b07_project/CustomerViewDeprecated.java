package com.example.b07_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CustomerViewDeprecated extends AppCompatActivity {
    int auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_view);
        UserServices userServices = new UserServices();
        auth = userServices.getCurrentUserAuth();
    }
    public void transitionToProfile(View view){
        Intent intent = new Intent(this, profile.class);
        intent.putExtra("auth", auth);
        startActivity(intent);
    }

    public void transitionToUpComingEvents(View view){
        Intent intent = new Intent(this, UpcomingEventsDriver.class);
        intent.putExtra("auth", auth);
        startActivity(intent);
    }

    public void transitionToVenuePage(View view){
        Intent intent = new Intent(this, VenuePageDennt.class);
        intent.putExtra("auth", auth);
        startActivity(intent);
    }

    public void transitionToAddEvent(View view)
    {
        Intent addVenue = new Intent(this, ChooseVenue.class);
        addVenue.putExtra("auth", auth);
        startActivity(addVenue);
    }
}