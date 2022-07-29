package com.example.b07_project.Denny;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.b07_project.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddEvent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
    }

    public void createEvent(View view)
    {
        EditText editText = (EditText) findViewById(R.id.eventName);
        String eventName = editText.getText().toString();

        TextView textView = findViewById(R.id.textView2);
        textView.setText(eventName);

        Event event = new Event(eventName);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        myRef.child("Denny").setValue(event);



    }
}