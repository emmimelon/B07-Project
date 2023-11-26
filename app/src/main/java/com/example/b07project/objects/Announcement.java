package com.example.b07project.objects;

public class Announcement {
    String date;
    String title;
    String description;
    String creator;
    int image;

    public Announcement(String date, String title, String description, String creator, int image) {
        this.date = date;
        this.title = title;
        this.description = description;
        this.creator = creator;
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String name) {
        this.date = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String complaint_text) {
        this.title = complaint_text;
    }

    public String getCreator() { return creator; }

    public void setCreator(String creator) { this.creator = creator; }
    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
