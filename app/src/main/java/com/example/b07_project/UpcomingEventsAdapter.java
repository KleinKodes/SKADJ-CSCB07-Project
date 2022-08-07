package com.example.b07_project;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
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
    public static String currClass;

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
//        textView.setHint(event.get);
        Log.i("Jacky", String.valueOf(event.getId()));
        Button button = holder.goToButton;
        button.setText("View Event");
        button.setEnabled(true);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int eventId = event.getId();
                Log.i("Jacky", "pre-event: "+eventId);
                currClass = "UpcomingEvent";
                Intent intent = new Intent(v.getContext(), ActivityDesc.class);
                intent.putExtra("eventId", eventId);
                intent.putExtra("currClass", currClass);
                intent.putExtra("auth", 1);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventsList.size();
    }

}
