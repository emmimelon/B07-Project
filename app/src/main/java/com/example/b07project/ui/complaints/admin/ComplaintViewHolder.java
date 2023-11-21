package com.example.b07project.ui.complaints.admin;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b07project.ComplaintViewInterface;
import com.example.b07project.R;

public class ComplaintViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView nameView, complaintView;
    public ComplaintViewHolder(@NonNull View itemView, ComplaintViewInterface complaintViewInterface) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageview);
        nameView = itemView.findViewById(R.id.name);
        complaintView = itemView.findViewById(R.id.complaint);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (complaintViewInterface != null) {
                    int pos = getBindingAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        complaintViewInterface.onItemClick(pos);
                    }
                }
            }
        });
    }
}
