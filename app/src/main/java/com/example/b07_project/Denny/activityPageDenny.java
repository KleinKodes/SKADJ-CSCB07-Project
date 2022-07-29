package com.example.b07_project.Denny;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.b07_project.R;

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

}