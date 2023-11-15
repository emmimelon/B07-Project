package com.example.b07project.objects;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Complaint {
    public String utorid;
    public String complaintTitle;
    public String complaintDescription;
    private DatabaseReference mDatabase;
    public Complaint(String utorid, String complaintTitle, String complaintDescription) {
        this.utorid = utorid;
        this.complaintTitle = complaintTitle;
        this.complaintDescription = complaintDescription;
    }
    public void writeNewComplaint(String utorid, String complaintTitle, String complaintDescription) {
        Complaint complaint = new Complaint(utorid, complaintTitle, complaintDescription);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Complaints").child(utorid).setValue(complaint);
    }
}
