package com.example.b07project.objects;

import android.provider.ContactsContract;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;

public class Complaint {
    private String id;
    private String complaintTitle;
    private String complaintDescription;
    private DatabaseReference mDatabase;
    public LocalDate date;
    boolean exists = false;
    public Complaint(String email, String complaintTitle, String complaintDescription) {
        this.id = email;
        this.complaintTitle = complaintTitle;
        this.complaintDescription = complaintDescription;
        this.date = LocalDate.now();
    }
    public void writeNewComplaint() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Complaints").child(date.toString()).child(id).child(complaintTitle).setValue(complaintDescription);
    }
}
