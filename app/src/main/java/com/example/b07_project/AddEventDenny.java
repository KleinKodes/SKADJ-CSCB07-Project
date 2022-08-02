package com.example.b07_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddEventDenny extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event_denny);

        View home = findViewById(R.id.homeButton);
        home.setOnClickListener(new Navigation());
        View profile = findViewById(R.id.profileButton);
        profile.setOnClickListener(new Navigation());

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Venues");
        getdata();
    }

    private void getdata() {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> list = new ArrayList<String>();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    Venue venue = snap.getValue(Venue.class);
                    list.add(venue.getName());
                }

                Spinner sports = (Spinner) findViewById(R.id.eventVenueSpinner);

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_spinner_item, list);

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sports.setAdapter(adapter);
                initializeSports();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println(error);
            }
        });
    }

    private void setSportDropdown(ArrayList<String> sportList) {


        Spinner sports = (Spinner) findViewById(R.id.eventSportSpinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, sportList);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sports.setAdapter(adapter);

    }

    public void initializeSports() {
        ArrayList<String> list = new ArrayList<String>();
        databaseReference = firebaseDatabase.getReference("Venues");


        Spinner sports = (Spinner) findViewById(R.id.eventVenueSpinner);
        sports.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();


                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        List<String> list = new ArrayList<String>();
                        System.out.println(snapshot.getChildrenCount());
                        for (DataSnapshot snap : snapshot.getChildren()) {
                            Venue venue = snap.getValue(Venue.class);
                            System.out.println(venue.getSports());
                            if (venue.getName() == parent.getItemAtPosition(position).toString()) {
                                setSportDropdown(venue.getSports());
                                break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        System.out.println(error);
                    }
                });
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
}
