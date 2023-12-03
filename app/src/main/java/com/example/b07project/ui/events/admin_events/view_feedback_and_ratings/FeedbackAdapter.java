package com.example.b07project.ui.events.admin_events.view_feedback_and_ratings;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b07project.R;

import java.util.List;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.FeedbackViewHolder> {

    private final List<FeedbackModel> feedbackList;

    public FeedbackAdapter(List<FeedbackModel> feedbackList) {
        this.feedbackList = feedbackList;
    }

    @NonNull
    @Override
    public FeedbackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_feedback_item, parent, false);
        return new FeedbackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedbackViewHolder holder, int position) {
        FeedbackModel feedback = feedbackList.get(position);
        holder.userName.setText(feedback.getUserName());
        holder.comment.setText(feedback.getComment());
        holder.rating.setRating(feedback.getRating());
    }

    @Override
    public int getItemCount() {
        return feedbackList.size();
    }

    static class FeedbackViewHolder extends RecyclerView.ViewHolder {
        TextView userName;
        TextView comment;
        RatingBar rating;

        public FeedbackViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.userName);
            comment = itemView.findViewById(R.id.userComment);
            rating = itemView.findViewById(R.id.ratingBar);
        }
    }
}