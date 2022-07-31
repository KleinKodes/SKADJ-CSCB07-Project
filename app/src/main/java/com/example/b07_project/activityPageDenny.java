package com.example.b07_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class activityPageDenny extends AppCompatActivity {
    public static String activity;
    public static String[] activityInfo = new String[5];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_denny);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public void addCard(View view) {
        LinearLayout layout = (LinearLayout) findViewById(R.id.cardList);
        View v = LayoutInflater.from(this).inflate(R.layout.activity_card2, null);
        layout.addView(v);
        addCard("d","d","d","d","d");
    }

    public void transtitionToDesc(View view){
        ViewGroup newView = (ViewGroup) view;

        TextView date = (TextView) newView.getChildAt(0);
        TextView start = (TextView) newView.getChildAt(1);
        TextView end = (TextView) newView.getChildAt(2);

        ViewGroup eventContainer = (ViewGroup) newView.getChildAt(4);
        TextView event = (TextView) eventContainer.getChildAt(0);
        TextView host = (TextView) eventContainer.getChildAt(1);

        Intent addProfile = new Intent(this, ActivityDesc.class);
        activityInfo[0] = date.getText().toString();
        activityInfo[1] = start.getText().toString();
        activityInfo[2] = end.getText().toString();
        activityInfo[3] = event.getText().toString();
        activityInfo[4] = host.getText().toString();

        addProfile.putExtra(activity, activityInfo);
        startActivity(addProfile);
    }

    public void addCard(String event, String host, String date, String start, String end) {
        LinearLayout layout = (LinearLayout) findViewById(R.id.cardList);
        View v = LayoutInflater.from(this).inflate(R.layout.activity_card2, null);
        layout.addView(v);

        int index = layout.getChildCount()-1;

        ViewGroup newView = (ViewGroup) layout.getChildAt(index);
        newView = (ViewGroup) newView.getChildAt(0);
        newView = (ViewGroup) newView.getChildAt(0);

        TextView currDate = (TextView) newView.getChildAt(0);
        TextView currStart = (TextView) newView.getChildAt(1);
        TextView currEnd = (TextView) newView.getChildAt(2);

        ViewGroup eventContainer = (ViewGroup) newView.getChildAt(4);
        TextView currEvent = (TextView) eventContainer.getChildAt(0);
        TextView currHost = (TextView) eventContainer.getChildAt(1);

        currEvent.setText(event);
        currStart.setText(start);
        currEnd.setText(end);
        currDate.setText(date);
        currHost.setText(host);
    }

}