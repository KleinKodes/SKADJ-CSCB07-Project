package com.example.b07_project;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class VenueAdapter extends RecyclerView.Adapter<VenueAdapter.ViewHolder>{

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView venueTextView;
        public Button goToButton;

        public ViewHolder(View itemView){
            super(itemView);
            venueTextView = itemView.findViewById(R.id.venueName);
            goToButton = itemView.findViewById(R.id.go_to_button);
        }
    }

    private ArrayList<Venue> Venues;

    public VenueAdapter(ArrayList<Venue> Venues){
        this.Venues = Venues;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View venueView = inflater.inflate(R.layout.listlayout, parent, false);

        ViewHolder viewHolder = new ViewHolder(venueView);
        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Venue venue = Venues.get(position);

        TextView textView = holder.venueTextView;
        textView.setText(venue.getName());
        Button button = holder.goToButton;
        button.setText("Go");
        button.setEnabled(true);
    }

    @Override
    public int getItemCount() {
        return Venues.size();
    }

}
