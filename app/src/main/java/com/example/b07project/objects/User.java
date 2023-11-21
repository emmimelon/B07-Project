package com.example.b07project.objects;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class User {
    private static String utorid;
    private String password;

    public String userType;
    private DatabaseReference mDatabase;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String utorid, String password, String userType) {
        this.utorid = utorid;
        this.password = password;
        this.userType = userType;
    }
    public String getUTORid(){
        return utorid;
    }
    public void writeNewUser(String utorid, String password, String userType) {
        User user = new User(utorid, password, userType);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child(utorid).setValue(user);
    }
}
