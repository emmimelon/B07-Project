package com.example.b07project.ui.complaints.admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b07project.R;
import com.example.b07project.objects.admin.Complaint;

import java.util.List;

public class ComplaintAdapter extends RecyclerView.Adapter<ComplaintViewHolder> {

    Context context;
    List<Complaint> complaints;
    private final ComplaintViewInterface complaintViewInterface;

    public ComplaintAdapter(Context context, List<Complaint> complaints, ComplaintViewInterface complaintViewInterface) {
        this.context = context;
        this.complaints = complaints;
        this.complaintViewInterface = complaintViewInterface;
    }

    @NonNull
    @Override
    public ComplaintViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ComplaintViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view, parent, false), complaintViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ComplaintViewHolder holder, int position) {
        holder.nameView.setText(complaints.get(position).getName());
        holder.complaintView.setText(complaints.get(position).getTitle());
        holder.imageView.setImageResource(complaints.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return complaints.size();
    }
}
