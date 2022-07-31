package com.example.b07_project;

import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {

        //private String[] localDataSet;
        private ArrayList<Venue> venueArrayList;
        private int auth;


        /**
         * Provide a reference to the type of views that you are using
         * (custom ViewHolder).
         */
        public static class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView venueName;
            private final CardView cardView;
            private final TextView venueSports;
            private final Button selectVenue;

            public ViewHolder(View view) {
                super(view);
                // Define click listener for the ViewHolder's View

                venueName = (TextView) view.findViewById(R.id.venue_name);
                cardView = (CardView) view.findViewById(R.id.cardView);
                venueSports = (TextView) view.findViewById(R.id.venue_sports);
                selectVenue = (Button) view.findViewById(R.id.select_venue);
            }

            public TextView getVenueName() {
                return venueName;
            }
            public TextView getVenueSports() {return venueSports;}
            public Button getSelectVenue() {return selectVenue;}
            public CardView getCardView() {return cardView;}

        }

        /**
         * Initialize the dataset of the Adapter.
         *
         * @param venueArrayList ArrayList<Venue> containing the data to populate views to be used
         * by RecyclerView.
         */
        public RecycleViewAdapter(ArrayList<Venue>venueArrayList, int auth) {
            this.venueArrayList= venueArrayList;
            this.auth = auth;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view, which defines the UI of the list item
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.venue_card, viewGroup, false);

            return new ViewHolder(view);
        }



    // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {

            // Get element from your dataset at this position and replace the
            // contents of the view with that element
            Venue venue = venueArrayList.get(position);
            String concatenation = "";
            if (venue.getSports() != null) for (String cat: venue.getSports()) concatenation+=cat + ", ";


            viewHolder.getVenueName().setText(venue.name);

            if (!concatenation.isEmpty()) viewHolder.getVenueSports().setText(concatenation.substring(0, concatenation.length() - 2));
            viewHolder.getSelectVenue().setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(v.getContext(), ChooseSport.class);

                    if(auth == 1) {
                        intent.setClass(v.getContext(), AddVenue.class);
                        intent.putExtra("mode", 1);
                    }




                    intent.putStringArrayListExtra("sports", venue.getSports());
                    intent.putExtra("venue", venue.getName());
                    intent.putExtra("venueId", venue.getId());
                    intent.putExtra("auth", auth);
                    v.getContext().startActivity(intent);





                }
            });
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return venueArrayList.size();
        }


}
