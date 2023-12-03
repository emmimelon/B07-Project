package com.example.b07project.ui.events.admin_events.view_users;

public class UserModel {
    private String name;

    public UserModel() {
        // Empty constructor needed for Firebase
    }

    public UserModel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}