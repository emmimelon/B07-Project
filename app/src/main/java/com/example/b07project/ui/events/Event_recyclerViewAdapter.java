package com.example.b07project.ui.events;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.b07project.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Event_recyclerViewAdapter extends RecyclerView.Adapter<Event_recyclerViewAdapter.myViewHolder>{

    Context context;
    ArrayList<EventsModel> eventsModels;
    public Event_recyclerViewAdapter(Context context, ArrayList<EventsModel> eventsModels){
        this.context = context;
        this.eventsModels = eventsModels;
    }
    @NonNull
    @Override
    public Event_recyclerViewAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row, parent, false);
        return new Event_recyclerViewAdapter.myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Event_recyclerViewAdapter.myViewHolder holder, int position) {
        holder.name.setText(eventsModels.get(position).getEventName());
        holder.location.setText(eventsModels.get(position).getEventLocation());
        holder.date.setText(eventsModels.get(position).getEventDate());
    }

    @Override
    public int getItemCount() {
        return eventsModels.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        TextView location;
        TextView date;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.eventName);
            location = itemView.findViewById(R.id.eventLocation);
            date = itemView.findViewById(R.id.eventDate);
        }
    }
}
