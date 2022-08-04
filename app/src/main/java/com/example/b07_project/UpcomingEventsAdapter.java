package com.example.b07_project;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UpcomingEventsAdapter extends RecyclerView.Adapter<UpcomingEventsAdapter.ViewHolder>{

    private ArrayList<Event> eventsList;

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView upcomingTextView;
        public Button goToButton;

        public ViewHolder(View itemView){
            super(itemView);
            upcomingTextView = itemView.findViewById(R.id.textName);
            goToButton = itemView.findViewById(R.id.select_venue);
        }
    }

    public UpcomingEventsAdapter(ArrayList<Event> eventsList){
        this.eventsList = eventsList;
    }

    public UpcomingEventsAdapter() {

    }

    public void setUpcomingEventsAdapterList (ArrayList<Event> eventsList){
        this.eventsList = eventsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View venueView = inflater.inflate(R.layout.listlayout, parent, false);

        return new ViewHolder(venueView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Event event = eventsList.get(position);

        TextView textView = holder.upcomingTextView;
        textView.setText(event.getName());
        Button button = holder.goToButton;
        button.setText("View Event");
        button.setEnabled(true);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ActivityDesc.class);

//                if(auth == 1) {
//                    intent.setClass(v.getContext(), AddVenue.class);
//                    intent.putExtra("mode", 1);
//                    intent.putExtra("auth", 1);
//                }

//                intent.putStringArrayListExtra("sports", venue.getSports());
//                intent.putExtra("venue", venue.getName());
//                intent.putExtra("venueId", venue.getId());
//                intent.putExtra("auth", auth);
                v.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return eventsList.size();
    }

}
