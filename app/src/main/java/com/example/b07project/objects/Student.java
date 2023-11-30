package com.example.b07project.objects;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class Student extends User{
    private ArrayList<String> complaints;

    public Student(FirebaseUser user){
        super(user, "Student");
    }
    public void getMyComplaints(){
    
    }

}
