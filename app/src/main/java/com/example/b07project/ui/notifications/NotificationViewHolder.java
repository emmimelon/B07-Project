package com.example.b07project.ui.notifications;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b07project.R;

public class NotificationViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView typeView, titleView;
    public NotificationViewHolder(@NonNull View itemView, NotificationsViewInterface notificationsViewInterface) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageview_notification);
        typeView = itemView.findViewById(R.id.notification_type);
        titleView = itemView.findViewById(R.id.notification_title);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (notificationsViewInterface != null) {
                    int pos = getBindingAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        notificationsViewInterface.onItemClick(pos);
                    }
                }
            }
        });
    }
}
