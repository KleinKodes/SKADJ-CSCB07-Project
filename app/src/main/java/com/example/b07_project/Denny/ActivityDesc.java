package com.example.b07_project.Denny;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.b07_project.R;

public class ActivityDesc extends AppCompatActivity {
    String[] activityInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desc);
        Intent intent = getIntent();
        activityInfo= intent.getStringArrayExtra(activityPageDenny.activity);
        initializevalues();
    }

    public void initializevalues(){

        ViewGroup infoContainer = (ViewGroup) findViewById(R.id.infoContainer);
        TextView host = (TextView)infoContainer.getChildAt(0);
        TextView start = (TextView)infoContainer.getChildAt(1);
        TextView end = (TextView)infoContainer.getChildAt(2);
        TextView date = (TextView)infoContainer.getChildAt(3);
        TextView activity = (TextView) findViewById(R.id.activity);

        host.setText(activityInfo[4]);
        start.setText(activityInfo[1]);
        end.setText(activityInfo[2]);
        date.setText(activityInfo[0]);
        activity.setText(activityInfo[3]);
    }

    public void backToActivities(View view){
        finish();
    }
}