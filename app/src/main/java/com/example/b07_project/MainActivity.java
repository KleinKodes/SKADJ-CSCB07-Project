package com.example.b07_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void transitionToLogin(View view)
    {
        Intent addEvent = new Intent(this, LoginActivity.class);
        startActivity(addEvent);
    }

    public void transitionToSignUp(View view){
        Intent addEvent = new Intent(this, SignUpActivity.class);
        startActivity(addEvent);
    }

    public void transitionToAddEvent(View view)
    {
        Intent addEvent = new Intent(this, AddEvent.class);
        startActivity(addEvent);
    }

}