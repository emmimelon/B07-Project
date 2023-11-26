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
import java.util.List;

public class Event_recyclerViewAdapter extends RecyclerView.Adapter<Event_recyclerViewAdapter.myViewHolder>{

    private final EventRVInterface recyclerViewInterface;
    Context context;
    List<EventsModel> eventsModels;
    public Event_recyclerViewAdapter(Context context, List<EventsModel> eventsModels,
                                     EventRVInterface recyclerViewInterface){
        this.context = context;
        this.eventsModels = eventsModels;
        this.recyclerViewInterface = recyclerViewInterface;
    }
    @NonNull
    @Override
    public Event_recyclerViewAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_format, parent, false);
        return new Event_recyclerViewAdapter.myViewHolder(view, recyclerViewInterface);
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
        public myViewHolder(@NonNull View itemView, EventRVInterface recyclerViewInterface) {
            super(itemView);

            name = itemView.findViewById(R.id.eventName);
            location = itemView.findViewById(R.id.eventLocation);
            date = itemView.findViewById(R.id.eventDate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recyclerViewInterface != null){
                        int pos = getAdapterPosition();

                        if(pos != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }
}
