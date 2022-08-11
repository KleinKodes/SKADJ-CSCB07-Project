package com.example.b07_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class activityPageDenny extends AppCompatActivity {
    public static String activity;
    public static String userId;
    public static Boolean isThisMyEvent = false;
    public boolean mode;
    private String firstName;
    public int venueId;
    UserServices userServices;
    private int pStatus=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        userServices = new UserServices();

        setContentView(R.layout.loading_activity);  
        ProgressBar p = (ProgressBar)findViewById(R.id.progressBar);
        Handler h = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(pStatus < 100){
                    pStatus++;
                    android.os.SystemClock.sleep(10);
                    h.post(new Runnable() {
                        @Override
                        public void run() {
                            p.setProgress(pStatus);
                        }
                    });
                }
            }
        }).start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run(){
                setContentView(R.layout.activity_page_denny);

                LayoutInflater layoutInflater = (LayoutInflater)
                        getSystemService(LAYOUT_INFLATER_SERVICE);
                View loadingView = layoutInflater.inflate(R.layout.loading_activity, null);


                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                Intent intent = getIntent();

                mode = intent.getBooleanExtra("approvalNeeded", false);
                firstName = userServices.getCurrentUserName();
                venueId = intent.getIntExtra("venueId", -1);
                if(firstName != null){
                    TextView textView = findViewById(R.id.profileUserName);
                    textView.setText(firstName);
                }

                View home = findViewById(R.id.homeButton);
                home.setOnClickListener(new Navigation());
                View profile = findViewById(R.id.profileButton);
                profile.setOnClickListener(new Navigation());
                View logOut = findViewById(R.id.logOutButton);
                logOut.setOnClickListener(new Navigation());
                userId = userServices.getCurrentUserId();
                if (userId == null) userId = "unset";
                if (mode){
                    TextView textView = (TextView) findViewById(R.id.upcomingEventHeaderDenny);
                    textView.setText("Unapproved Events");

                    View navView =(View) logOut.getParent();
                    navView.setVisibility(View.GONE);

                    View addPost = findViewById(R.id.addPost);
                    addPost.setVisibility(View.GONE);

                    View returnHome = findViewById(R.id.returnHome);
                    returnHome.setVisibility(View.VISIBLE);

                    TextView profileName = (TextView) findViewById(R.id.profileUserName);
                    profileName.setText("Admin");

                }
                else{
                    View returnHome = findViewById(R.id.returnHome);
                    returnHome.setVisibility(View.GONE);
                }


                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference eventRef = database.getReference("Events");
                DatabaseReference userRef = database.getReference("Users");

                eventRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        Event event;
                        for(DataSnapshot i : task.getResult().getChildren())
                        {
                            event = i.getValue(Event.class);
                            Event finalEvent = event;
                            if (((!mode && event.approved) || mode && !event.approved )&& ((venueId == -1) || event.getVenueId() == venueId))
                                userRef.child(event.getOwnerId() + "").child("firstName").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                                        String hostName = task.getResult().getValue(String.class);
                                        addCard(findViewById(R.id.profilePage), finalEvent, hostName);
                                    }
                                });



                        }

                    }
                });
            }
        }, 1200);


    }

    public void addCard(View view, Event event, String hostName) {
        LinearLayout layout = (LinearLayout) findViewById(R.id.cardList);
        View v = LayoutInflater.from(this).inflate(R.layout.activity_card2, null);
        TextView textView = v.findViewById(R.id.hostName);
        if (hostName == null) hostName = "default";
        textView.setText(hostName);
        textView.setHint(event.getOwnerId());
        textView = v.findViewById(R.id.cardEventDate);
        textView.setText(event.getStartDateString());
        textView = v.findViewById(R.id.startTime);
        textView.setText("Start: "+event.getStartTimeString());
        textView = v.findViewById(R.id.endTime);
        textView.setText("End: "+event.getEndTimeString());
        textView = v.findViewById(R.id.profileEventName);
        textView.setText(event.getName());
        textView.setHint(event.getId() + "");
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transitionToDesc(v);
            }
        });
        layout.addView(v);
    }

    public void transitionToDesc(View view){
        Log.i("debug info", "view with id: " + view.getId() + " pressed");
        if (view.getId() != R.id.cardContainer) return;

        ViewGroup newView = (ViewGroup) view;

        ViewGroup eventContainer1 = (ViewGroup) newView.getChildAt(1);
        TextView date = (TextView) eventContainer1.getChildAt(0);
        TextView start = (TextView) eventContainer1.getChildAt(1);
        TextView end = (TextView) eventContainer1.getChildAt(2);

        ViewGroup eventContainer2 = (ViewGroup) newView.getChildAt(0);
        TextView event = (TextView) eventContainer2.getChildAt(0);
        TextView host = (TextView) eventContainer2.getChildAt(1);

        Intent addProfile = new Intent(this, ActivityDesc.class);

        int eventId = Integer.parseInt(event.getHint().toString());
        isThisMyEvent = host.getHint().toString().equals(userId);
        Log.i("do i own this event?", isThisMyEvent.toString());

        addProfile.putExtra("isThisMyEvent", isThisMyEvent);
        addProfile.putExtra("host", host.getText());
        addProfile.putExtra("eventId", eventId);

        addProfile.putExtra("approvalNeeded", mode);

        startActivity(addProfile);
        if (mode) finish();
    }

    private void transitionToViewUpcomingEvents(View view){
        Intent intent = new Intent(this, UpcomingEventsDriver.class);
        startActivity(intent);
    }

    public void transitionToAddEvent(View view){
        Intent intent = new Intent(this, AddEventDenny.class);
        intent.putExtra("CurrVenue", venueId);
        intent.putExtra("index", getIntent().getIntExtra("index", 0));
        startActivity(intent);
    }

    public void backToPage(View view){
        super.onBackPressed();
        finish();
    }

}

