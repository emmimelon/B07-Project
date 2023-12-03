package com.example.b07project.objects.admin;

import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class Complaint implements Comparable<Complaint>{
    String name;
    String title;
    String description, date;
    int image;

    public Complaint(String name, String title, String description, int image, String date) {
        this.name = name;
        this.title = title;
        this.description = description;
        this.image = image;
        this.date = date;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int compareTo(Complaint o) {
        return this.getDate().compareTo(o.getDate());
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof Complaint)) return false;
        Complaint other = (Complaint) obj;
        return this.title.equals(other.title) && this.name.equals(other.name);
    }
}
