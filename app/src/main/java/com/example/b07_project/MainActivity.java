package com.example.b07_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void transitionToAddEvent(View view)
    {
        Intent addEvent = new Intent(this, AddEvent.class);
        startActivity(addEvent);
    }

    public void transitionToUpcomingEvents(View view){
        Intent upcoming = new Intent(this, UpcomingEventsDriver.class);
        startActivity(upcoming);
    }

}