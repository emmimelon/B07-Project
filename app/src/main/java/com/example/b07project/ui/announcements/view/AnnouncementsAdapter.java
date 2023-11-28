package com.example.b07project.ui.announcements.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b07project.R;
import com.example.b07project.objects.Announcement;
import com.example.b07project.ui.complaints.admin.ComplaintViewHolder;

import org.checkerframework.checker.units.qual.A;

import java.util.List;

public class AnnouncementsAdapter extends RecyclerView.Adapter<AnnouncementsViewHolder> {
    Context context;
    List<Announcement> announcements;

    private final AnnouncementsViewInterface announcementsViewInterface;

    public AnnouncementsAdapter(Context context, List<Announcement> announcements, AnnouncementsViewInterface announcementsViewInterface){
        this.context = context;
        this.announcements = announcements;
        this.announcementsViewInterface = announcementsViewInterface;
    }

    @NonNull
    @Override
    public AnnouncementsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AnnouncementsViewHolder(LayoutInflater.from(context).inflate(R.layout.announcement_item_view, parent, false), announcementsViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull AnnouncementsViewHolder holder, int position) {
    holder.dateView.setText(FormatDate.formatDate(announcements.get(position).getDate()));
    holder.announcementView.setText(announcements.get(position).getTitle());
    holder.imageView.setImageResource(announcements.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return announcements.size();
    }
}
