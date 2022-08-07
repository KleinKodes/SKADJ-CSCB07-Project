package com.example.b07_project;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {
        private ArrayList<Venue> venueArrayList;
        private int auth;
        public static class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView venueName;
            private final CardView cardView;
            private final TextView venueSports;
            private final Button selectVenue;

            public ViewHolder(View view) {
                super(view);
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

        public RecycleViewAdapter(ArrayList<Venue>venueArrayList, int auth) {
            this.venueArrayList= venueArrayList;
            this.auth = auth;
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.venue_card, viewGroup, false);
            return new ViewHolder(view);
        }
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {
            Venue venue = venueArrayList.get(position);
            String concatenation = "";
            if (venue.getSports() != null) for (String cat: venue.getSports()) concatenation+=cat + ", ";


            viewHolder.getVenueName().setText(venue.name);

            if (!concatenation.isEmpty()) viewHolder.getVenueSports().setText(concatenation.substring(0, concatenation.length() - 2));
            viewHolder.getSelectVenue().setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new    Intent(v.getContext(), AddVenue.class);
                    intent.putExtra("mode", 1);
                    intent.putStringArrayListExtra("sports", venue.getSports());
                    intent.putExtra("venue", venue.getName());
                    intent.putExtra("venueId", venue.getId());
                    v.getContext().startActivity(intent);
                }
            });
        }
        @Override
        public int getItemCount() {
            return venueArrayList.size();
        }
}
