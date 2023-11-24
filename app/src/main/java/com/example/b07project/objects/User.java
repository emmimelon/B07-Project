package com.example.b07project.objects;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class User {
    private FirebaseUser firebaseUser;
    private String id;
    private String email;
    private String name;
    private String userType;
    private ArrayList<String> registeredEvents;
    private DatabaseReference mDatabase;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }
    public User(FirebaseUser user, String userType) {
        this.firebaseUser = user;
        this.id = user.getUid();
        this.email = user.getEmail();
        this.name = user.getDisplayName();
        this.userType = userType;
        this.registeredEvents = null;
    }
    public String getEmail() { return email;}
    public String getUserType(){return userType;}
    public String getName(){return name;}
    public ArrayList<String> getRegisteredEvents() {
        return registeredEvents;
    }
    public void updateUser(){
        email = firebaseUser.getEmail();
        name = firebaseUser.getDisplayName();
    }
    public void addNewUser() {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(id);
        mDatabase.child("Email").setValue(firebaseUser.getEmail());
        mDatabase.child("Name").setValue(firebaseUser.getDisplayName());
        mDatabase.child("Type").setValue(userType);
        mDatabase.child("Registered Events").setValue(registeredEvents);
    }
    /*public void registerEvent(Event event){
        registeredEvents.add(event);
    }*/
}
