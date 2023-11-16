package com.example.b07project;

public class Complaint {
    String name;
    String complaint_text;
    int image;

    public Complaint(String name, String complaint, int image) {
        this.name = name;
        this.complaint_text = complaint;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComplaint_text() {
        return complaint_text;
    }

    public void setComplaint_text(String complaint_text) {
        this.complaint_text = complaint_text;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
