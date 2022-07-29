package com.example.b07_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.b07_project.Anthony.AddEvent;
import com.example.b07_project.Anthony.AddVenue;

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

    public void transitionToAddVenue(View view)
    {
        Intent addVenue = new Intent(this, AddVenue.class);
        startActivity(addVenue);
    }

}