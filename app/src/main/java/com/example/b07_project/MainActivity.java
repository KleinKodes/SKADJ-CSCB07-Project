package com.example.b07_project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;



public class MainActivity extends AppCompatActivity { //


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_activity);


       Intent intent = new Intent(this, WrapperActivity.class);


       startActivity(intent);
       finish();

    }
}
