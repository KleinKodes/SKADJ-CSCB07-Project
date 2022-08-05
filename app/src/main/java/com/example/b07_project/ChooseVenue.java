package com.example.b07_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class ChooseVenue extends AppCompatActivity {

    private int pStatus=0;
    public static final ArrayList<Venue> venues = new ArrayList<Venue>();
    ConstraintLayout constraintLayout;
    int id_count = 0;
    public static ArrayList<String> sportsPass = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_venue_sport);

        int auth = this.getIntent().getIntExtra("auth", 0);

        RecyclerView recyclerView = findViewById(R.id.rvVenues);
        RecycleViewAdapter recycleViewAdapter = new RecycleViewAdapter(venues, auth);
        recyclerView.setAdapter(recycleViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

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
                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {



                    Venue venue =childSnapshot.getValue(Venue.class);
                    if (!venues.contains(venue)) {venues.add(venue); recycleViewAdapter.notifyItemInserted(venues.size() - 1);}


                }



            }

            public void onCancelled(DatabaseError error) {
                System.out.println("The read failed: " + error.getCode());
            }
        });
        
        //END BLOCK


    }

    private void extracted(Venue venue) {
        constraintLayout = findViewById(R.id.constraintLayout);

        CardView cardView;
        TextView venueName;
        TextView venueSports;
        Button selectVenue;
        String combinator;



            //reset values for new card
            combinator = "";
            cardView = new CardView(this);
            cardView.setId(id_count++);
            venueName = new TextView(this);
            venueName.setId(id_count++);
            venueSports = new TextView(this);
            venueSports.setId(id_count++);
            selectVenue = new Button(this);
            selectVenue.setId(id_count++);
            selectVenue.setText("Select");
            selectVenue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    int sportsViewId = v.getId();
                    sportsViewId-=1;
                    int venueNameId = sportsViewId-1;
                    chooseVenue(v, sportsViewId, venueNameId, venue.getId());
                }
            });


            //loop through sports and add to sports text

            if (venue.sports != null){for (String s :venue.sports) combinator += s+ ", ";
            venueSports.setText(combinator.substring(0, combinator.length() - 2));}
            venueName.setText(venue.name);


        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);
        constraintSet.connect(venueName.getId(),ConstraintSet.BOTTOM,venueSports.getId(),ConstraintSet.TOP,16);
        constraintSet.connect(venueName.getId(),ConstraintSet.TOP,cardView.getId(),ConstraintSet.TOP,12);
        constraintSet.connect(venueSports.getId(), ConstraintSet.BOTTOM, cardView.getId(), ConstraintSet.BOTTOM, 12);
        constraintSet.connect(cardView.getId(), ConstraintSet.BOTTOM, cardView.getId(), ConstraintSet.BOTTOM, 12);

        constraintSet.applyTo(constraintLayout);

        venueName.setPadding(12, 12, 12, 12);
        venueSports.setPadding(12, 12, 12, 12);

        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );

        ViewGroup.LayoutParams params1 = new ViewGroup.LayoutParams(CardView.LayoutParams.WRAP_CONTENT, CardView.LayoutParams.WRAP_CONTENT);


        params.setMargins(12, 12, 12, 12);
        cardView.setLayoutParams(params1);
        venueName.setLayoutParams(params);
        venueSports.setLayoutParams(params);



        //set card attributes and add text views to card

            cardView.setCardBackgroundColor(255);
            cardView.setCardElevation((float) 1.2);
            cardView.setContentPadding(5, 5, 5, 5);
            cardView.addView(venueName);
            cardView.addView(venueSports);
            cardView.addView(selectVenue);


            constraintLayout.addView(cardView);





    }

    public void chooseVenue(View view, int sportsId, int venueNameId, int venueID){

        Intent intent = new Intent(this, ChooseSport.class);

        TextView sportsList = (TextView)findViewById(sportsId);
        TextView venueName = (TextView)findViewById(venueNameId);
        String temp2 = (String) sportsList.getText();
        for (String str : temp2.split(", ")) sportsPass.add(str);

        Log.i("sports-list:", sportsPass.toString());


        int auth = this.getIntent().getIntExtra("auth", 0);
        intent.putExtra("auth", auth);

        intent.putStringArrayListExtra("sports", sportsPass);
        intent.putExtra("venue", venueName.getText());
        intent.putExtra("venueId", venueID);


        startActivity(intent);
    }

}