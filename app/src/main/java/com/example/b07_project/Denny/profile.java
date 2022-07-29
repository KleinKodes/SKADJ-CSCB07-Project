package com.example.b07_project.Denny;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.example.b07_project.R;

public class profile extends AppCompatActivity {
    public static String tabState = "";
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Intent intent = getIntent();
        int state = intent.getIntExtra(profile.tabState,1);
        System.out.println(state);

        ConstraintLayout layout;
        if (state == 1){
            layout = (ConstraintLayout) findViewById(R.id.tab1);
        }
        else{
            layout = (ConstraintLayout) findViewById(R.id.tab2);
        }
        layout.setBackgroundTintList(ContextCompat.getColorStateList(this,R.color.dark_green));
    }

    public void addProfileCard(View view) {
        LinearLayout layout = (LinearLayout) findViewById(R.id.cardList);
        View v = LayoutInflater.from(this).inflate(R.layout.profile_card, null);
        layout.addView(v);
    }

    public void switchToCreate(View view) {
        Intent addProfile = new Intent(this, profile.class);
        addProfile.putExtra(tabState, 1);
        startActivity(addProfile);
    }

    public void switchToJoin(View view) {
        Intent addProfile = new Intent(this, profile.class);
        addProfile.putExtra(tabState, 2);
        startActivity(addProfile);
    }
}