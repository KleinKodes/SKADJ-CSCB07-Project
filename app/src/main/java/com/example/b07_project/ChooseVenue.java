package com.example.b07_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChooseVenue extends AppCompatActivity {

    private int pStatus=0;
    public final ArrayList<Venue> venues = new ArrayList<Venue>();
    ConstraintLayout constraintLayout;
    int id_count = 0;
    public ArrayList<String> sportsPass = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);

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
            public void run() {


                setContentView(R.layout.activity_choose_venue_sport);
                UserServices userServices = new UserServices();

                int auth = userServices.getCurrentUserAuth();

                RecyclerView recyclerView = findViewById(R.id.rvVenues);
                RecycleViewAdapter recycleViewAdapter = new RecycleViewAdapter(venues, auth);
                recyclerView.setAdapter(recycleViewAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(ChooseVenue.this));

                Log.i("status", "in choose venue view");

                //CountDownLatch countDownLatch = new CountDownLatch(1);

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference venueRef = database.getReference("Venues");


                Log.i("status", "potential crash site 1");

//


                //BLOCK EXPERIMENT
                venueRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //System.out.println("we are here");


                        //dataSnapshot.g
                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {


                            Venue venue = childSnapshot.getValue(Venue.class);
                            if (!venues.contains(venue)) {
                                venues.add(venue);
                                recycleViewAdapter.notifyItemInserted(venues.size() - 1);
                            }


                        }


                    }

                    public void onCancelled(DatabaseError error) {
                        System.out.println("The read failed: " + error.getCode());
                    }
                });
            }
        //END BLOCK
        }, 1200);
    }


    public void backToAdminView(View view)
    {
        Intent intent = new Intent(this, AdminActivity.class);
        startActivity(intent);
        finish();
    }


}