package com.example.b07project.ui.events.admin_events;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.b07project.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdminEventsAdapter extends RecyclerView.Adapter<AdminEventsAdapter.myViewHolder> {

    List<AdminEventsModel> adminEventsModels;
    private final AdminEventsInterface recyclerViewInterface;
    Context context;

    // Constructor
    public AdminEventsAdapter(Context context, List<AdminEventsModel> adminEventsModels, AdminEventsInterface recyclerViewInterface) {
        this.context = context;
        this.adminEventsModels = adminEventsModels;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    // ViewHolder class
    public static class myViewHolder extends RecyclerView.ViewHolder {
        public TextView eventName;
        public TextView eventDate;

        public myViewHolder(@NonNull View itemView, AdminEventsInterface recyclerViewInterface) {
            super(itemView);
            eventName = itemView.findViewById(R.id.event_name);
            eventDate = itemView.findViewById(R.id.date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recyclerViewInterface != null) {
                        int pos = getBindingAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION) {
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }
    @NonNull
    @Override
    public AdminEventsAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_admin_event_item, parent, false);
        return new AdminEventsAdapter.myViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminEventsAdapter.myViewHolder holder, int position) {
        holder.eventName.setText(adminEventsModels.get(position).getEventName());
        holder.eventDate.setText(adminEventsModels.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return adminEventsModels.size();
    }
}