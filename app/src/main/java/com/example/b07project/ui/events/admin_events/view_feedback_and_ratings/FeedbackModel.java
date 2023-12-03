package com.example.b07project.ui.events.admin_events.view_feedback_and_ratings;

public class FeedbackModel {
    private String userName;
    private String comment;
    private Long rating;

    public FeedbackModel() {
        // empty constructor
    }

    public FeedbackModel(String userName, String comment, Long rating) {
        this.userName = userName;
        this.comment = comment;
        this.rating = rating;
    }

    public String getUserName() {
        return userName;
    }
    public String getComment() {
        return comment;
    }
    public Long getRating() {
        return rating;
    }
}
