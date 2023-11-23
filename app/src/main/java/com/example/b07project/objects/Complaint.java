package com.example.b07project.objects;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Complaint {
    private String id;
    private String complaintTitle;
    private String complaintDescription;
    private DatabaseReference mDatabase;
    public Complaint(String email, String complaintTitle, String complaintDescription) {
        this.id = email;
        this.complaintTitle = complaintTitle;
        this.complaintDescription = complaintDescription;
    }
    public void writeNewComplaint() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Complaints").child(id).child(complaintTitle).setValue(complaintDescription);
    }
}
