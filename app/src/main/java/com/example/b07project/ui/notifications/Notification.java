package com.example.b07project.ui.notifications;

import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.time.LocalDate;

public class Notification implements Comparable<Notification>{
    String type, title, desc, creator, fullDate;
    int image;
    LocalDate date;

    public Notification(String type, String title, int image, LocalDate date, String desc, String creator, String fullDate) {
        this.type = type;
        this.title = title;
        this.image = image;
        this.date = date;
        this.desc = desc;
        this.creator = creator;
        this.fullDate = fullDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getFullDate() {
        return fullDate;
    }

    public void setFullDate(String fullDate) {
        this.fullDate = fullDate;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int compareTo(Notification o) {
        return this.getDate().compareTo(o.getDate());
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof Notification)) return false;
        Notification other = (Notification) obj;
        return this.title.equals(other.title) && this.date.equals(other.date);
    }
}
