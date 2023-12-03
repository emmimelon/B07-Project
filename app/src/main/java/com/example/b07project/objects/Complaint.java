package com.example.b07project.objects;

import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;

public class Complaint implements Comparable<Complaint>{
    private String id;
    private String complaintTitle;
    private String complaintDescription;
    private DatabaseReference mDatabase;
    public LocalDate date;
    private String previousDate;
    int complaintIcon;
    public Complaint(String id, String complaintTitle, String complaintDescription) {
        this.id = id;
        this.complaintTitle = complaintTitle;
        this.complaintDescription = complaintDescription;
        this.date = LocalDate.now();
    }
    public Complaint(String title, String description, String date, int image){
        complaintTitle = title;
        complaintDescription = description;
        previousDate = date;
        complaintIcon = image;
    }
    public void writeNewComplaint() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Complaints").child(id).child(complaintTitle).child("Date").setValue(date.toString());
        mDatabase.child("Complaints").child(id).child(complaintTitle).child("Description").setValue(complaintDescription);
    }
    public String getTitle(){
        return complaintTitle;
    }
    public String getDescription(){
        return complaintDescription;
    }
    public int getIcon(){
        return complaintIcon;
    }
    public String getDate(){ return previousDate;}

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int compareTo(Complaint o) {
        return this.getDate().compareTo(o.getDate());
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof Complaint)) return false;
        Complaint other = (Complaint) obj;
        return this.complaintTitle.equals(other.complaintTitle);
    }
}
