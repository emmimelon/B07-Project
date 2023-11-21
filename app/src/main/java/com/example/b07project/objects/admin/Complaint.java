package com.example.b07project.objects.admin;

public class Complaint {
    String name;
    String title;
    String description;
    int image;

    public Complaint(String name, String title, String description, int image) {
        this.name = name;
        this.title = title;
        this.description = description;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String complaint_text) {
        this.title = complaint_text;
    }

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
