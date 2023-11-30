package com.example.b07project.ui.complaints.student;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b07project.R;

public class StudentComplaintViewHolder extends RecyclerView.ViewHolder {
    ImageView icon;
    TextView title, description, date;
    public StudentComplaintViewHolder(@NonNull View itemView, StudentComplaintViewInterface studentComplaintViewInterface) {
        super(itemView);
        icon = itemView.findViewById(R.id.complaintsIcon);
        title = itemView.findViewById(R.id.pastTitle);
        date = itemView.findViewById(R.id.pastDate);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (studentComplaintViewInterface != null) {
                    int pos = getBindingAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        studentComplaintViewInterface.onItemClick(pos);
                    }
                }
            }
        });
    }
}
