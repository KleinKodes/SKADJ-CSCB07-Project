package com.example.b07_project.Denny;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
<<<<<<< HEAD:app/src/main/java/com/example/b07_project/AddEvent.java
import android.widget.DatePicker;
=======
>>>>>>> Denny:app/src/main/java/com/example/b07_project/Denny/AddEvent.java
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

<<<<<<< HEAD:app/src/main/java/com/example/b07_project/AddEvent.java
import java.util.ArrayList;
import java.util.GregorianCalendar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
=======
import com.example.b07_project.R;
>>>>>>> Denny:app/src/main/java/com/example/b07_project/Denny/AddEvent.java
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddEvent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        Intent intent = getIntent();

        TextView sportText = findViewById(R.id.textView100);
        sportText.setText(intent.getStringExtra("sport"));

        TextView venueText = findViewById(R.id.venueName);
        venueText.setText(intent.getStringExtra("venue"));


        final ArrayList<String> sports = new ArrayList<>();
        sports.add("Volleyball");
        sports.add("Quiddich");
        sports.add("Not league");
        sports.add("extreme seven eating");



    }

    public void createEvent(View view)
    {
        System.out.println("someone clicked the button");
        final int[] curMaxId = {-1}; //java made me use a final length 1 array instead of an integer idk y



        EditText editText = (EditText) findViewById(R.id.eventName);
        String eventName = editText.getText().toString();
        editText = (EditText) findViewById(R.id.eventCapacity);
        Event event = new Event(eventName);
        event.capacity = Integer.parseInt(editText.getText().toString());
        editText= (EditText) findViewById(R.id.eventDescription);
        event.eventDescription= editText.getText().toString();
        TimePicker timePicker = (TimePicker) findViewById(R.id.eventStartTime);

        DatePicker datePicker = (DatePicker) findViewById(R.id.eventDate);

        event.setStartDate(new GregorianCalendar(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), timePicker.getCurrentHour(), timePicker.getCurrentMinute()));
        timePicker = (TimePicker) findViewById(R.id.eventEndTime);
        event.setEndDate(new GregorianCalendar(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), timePicker.getCurrentHour(), timePicker.getCurrentMinute()));
        event.sport = getIntent().getStringExtra("sport");
        event.venueId = getIntent().getIntExtra("venueId", -1);




        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Events");
        Log.i("demo", "honestly idk");




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
                    //Log.i("demo", task.getResult().getValue().toString());

                    event.id=curMaxId[0]+1;
                event.ownerId = 0; //temporary until we make user class


                myRef.child(event.id + "").setValue(event);


                }
            }
        });



        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();

    }
}