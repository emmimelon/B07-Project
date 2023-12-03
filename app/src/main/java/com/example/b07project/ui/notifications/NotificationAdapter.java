package com.example.b07project.ui.notifications;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b07project.R;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationViewHolder> {

    Context context;
    List<Notification> notifications;
    private final NotificationsViewInterface notificationsViewInterface;

    public NotificationAdapter(Context context, List<Notification> notifications, NotificationsViewInterface notificationsViewInterface) {
        this.context = context;
        this.notifications = notifications;
        this.notificationsViewInterface = notificationsViewInterface;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotificationViewHolder(LayoutInflater.from(context).inflate(R.layout.notification_view, parent, false), notificationsViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        holder.typeView.setText(notifications.get(position).getType());
        holder.titleView.setText(notifications.get(position).getTitle());
        holder.imageView.setImageResource(notifications.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
