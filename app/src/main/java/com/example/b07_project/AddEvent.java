package com.example.b07_project;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
    }
}