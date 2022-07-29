package com.example.b07_project.Anthony;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import com.example.b07_project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddEvent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference venueRef = database.getReference("Venues");



        //BLOCK
        //The code below creates a new venue... temporary until admin view done

//        Venue venue = new Venue();
//        venue.id = 69;
//        venue.capacity = 100;
//        venue.name = "notPanam";
//        venue.description = "nothing at all";
        final ArrayList<String> sports = new ArrayList<>();
        sports.add("Volleyball");
        sports.add("Quiddich");
        sports.add("Not league");
        sports.add("extreme seven eating");

//        venue.sports = sports;

        //DatabaseReference venueRef = database.getReference("Venues");
//        venueRef.child(venue.id +"").setValue(venue);
//


        //event.venueId = venue.id;
        //ENDBLOCK


        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sports);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

//        venueRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            int venueId = 69;
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//
//                if (!task.isSuccessful()) {
//                    Log.e("demo", "Error getting data", task.getException());
//                } else {
//
//
//
//                }
//
//            }});


    }

    public void createEvent(View view)
    {
        System.out.println("someone clicked the button");
        final int[] curMaxId = {-1}; //java made me use a final length 1 array instead of an integer idk y
        EditText editText = (EditText) findViewById(R.id.eventName);
        String eventName = editText.getText().toString();


        TextView textView = findViewById(R.id.textView2);
        textView.setText(eventName);

        Event event = new Event(eventName);
        Spinner sportSpinner = findViewById(R.id.spinner);
        event.sport=sportSpinner.getSelectedItem().toString();


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Events");
        Log.i("demo", "honestly idk");

        //BLOCK
        //The code below creates a new venue... temporary until admin view done

//        Venue venue = new Venue();
//        venue.id = 69;
//        venue.capacity = 100;
//        venue.name = "notPanam";
//        venue.description = "nothing at all";
//        ArrayList<String> sports = new ArrayList<String>();
//        sports.add("Volleyball");
//        sports.add("Quiddich");
//        sports.add("Not league");
//        sports.add("extreme seven eating");
//
//        venue.sports = sports;
//
//        DatabaseReference venueRef = database.getReference("Venues");
//        venueRef.child(venue.id +"").setValue(venue);
//
//
event.venueId = 69;
        //ENDBLOCK






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
                    Log.i("demo", task.getResult().getValue().toString());

                    event.id=curMaxId[0]+1;
                event.ownerId = 0; //temporary until we make user class


                myRef.child(event.id + "").setValue(event);


                }
            }
        });





//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                System.out.println("we are here");
//                int tempId;
//                //dataSnapshot.g
//                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
//                    tempId  = childSnapshot.getValue(Event.class).id;
//                    Log.i("demo", "current max id: " + curMaxId[0]);
//                    if (tempId >= curMaxId[0]) curMaxId[0] = tempId;
//                    Log.i("demo", "id = " + tempId);
//                    Log.i("demo", "new current max id: " + curMaxId[0]);
//
//                }
//
//
//
//            }
//
//            public void onCancelled(DatabaseError error) {
//                System.out.println("The read failed: " + error.getCode());
//            }
//        });

        //Log.i("demo", "test if max cur has updated: " + curMaxId[0]);



    }
}