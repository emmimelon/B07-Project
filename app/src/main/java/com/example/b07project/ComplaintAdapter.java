package com.example.b07project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ComplaintAdapter extends RecyclerView.Adapter<ComplaintViewHolder> {

    Context context;
    List<Complaint> complaints;

    public ComplaintAdapter(Context context, List<Complaint> complaints) {
        this.context = context;
        this.complaints = complaints;
    }

    @NonNull
    @Override
    public ComplaintViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ComplaintViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ComplaintViewHolder holder, int position) {
        holder.nameView.setText(complaints.get(position).getName());
        holder.complaintView.setText(complaints.get(position).getComplaint_text());
        holder.imageView.setImageResource(complaints.get(position).getImage());

    }

    @Override
    public int getItemCount() {
        return complaints.size();
    }
}
