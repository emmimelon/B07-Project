package com.example.b07project;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ComplaintViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView nameView, complaintView;
    public ComplaintViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageview);
        nameView = itemView.findViewById(R.id.name);
        complaintView = itemView.findViewById(R.id.complaint);
    }
}
