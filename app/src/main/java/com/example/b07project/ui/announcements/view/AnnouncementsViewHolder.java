package com.example.b07project.ui.announcements.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b07project.R;

public class AnnouncementsViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    TextView dateView, announcementView;

    public AnnouncementsViewHolder(@NonNull View itemView, AnnouncementsViewInterface announcementsViewInterface) {
        super(itemView);
        imageView = itemView.findViewById(R.id.announcement_imageview);
        dateView = itemView.findViewById(R.id.announcement_date);
        announcementView = itemView.findViewById(R.id.announcement_title);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (announcementsViewInterface != null) {
                    int pos = getBindingAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        announcementsViewInterface.onItemClick(pos);
                    }
                }
            }
        });
    }
}
