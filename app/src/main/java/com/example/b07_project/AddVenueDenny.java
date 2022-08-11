package com.example.b07_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.view.View;

public class AddVenueDenny extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_venue_denny);

        View home = findViewById(R.id.homeButton);
        home.setOnClickListener(new Navigation());
        View profile = findViewById(R.id.profileButton);
        profile.setOnClickListener(new Navigation());
    }
}