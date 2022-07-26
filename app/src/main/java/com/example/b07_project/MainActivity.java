package com.example.b07_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.b07_project.ui.login.LoginActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent1 = new Intent(this, LoginActivity.class);
        startActivity(intent1);
    }

    public void transitionToAddEvent(View view)
    {
        Intent addEvent = new Intent(this, AddEvent.class);
        startActivity(addEvent);
    }

}