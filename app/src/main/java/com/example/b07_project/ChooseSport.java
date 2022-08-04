package com.example.b07_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

public class ChooseSport extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_sport);

        ArrayList<String> sports = new ArrayList<String>();

        Intent intent = getIntent();
        if (intent.getStringArrayListExtra("sports") != null)for (String s: intent.getStringArrayListExtra("sports")) sports.add(s);

        Spinner spinner = findViewById(R.id.spinner2);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sports);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
    }

    public void chooseSport(View view){
        Spinner sportSpinner = findViewById(R.id.spinner2);
        String sport = sportSpinner.getSelectedItem().toString();

        Intent intent = new Intent(this, AddEvent.class);
        intent.putExtra("venue", intent.getStringExtra("venue"));
        intent.putExtra("sport", sport);
        intent.putExtra("venueId", intent.getIntExtra("venueId", -1));

        startActivity(intent);
    }
}