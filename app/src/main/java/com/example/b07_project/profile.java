package com.example.b07_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

public class profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }

    public void addProfileCard(View view) {
        LinearLayout layout = (LinearLayout) findViewById(R.id.cardList);
        View v = LayoutInflater.from(this).inflate(R.layout.profile_card, null);
        layout.addView(v);
    }
}