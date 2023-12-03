package com.example.b07project.login;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.b07project.MainActivity;
import com.example.b07project.WelcomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class LoginPresenter {
    LoginModel model;
    LoginView view;
    public LoginPresenter(LoginView view, LoginModel model){
        this.model = model;
        this.view = view;
    }
    public void checkDB() {
        String password = view.getPassword();
        String email = view.getEmail();
        if (email == null || password == null){
            view.setResultText("Please fill in your user details.");
        }
        else {
            model.signIn(this, email, password);
        }
    }
    public void setViewText() {
        view.setResultText("This user does not exist.");
    }
    public void goTo(String userType){
        switch(userType){
            case "Student":
                view.enterStudentApp();
                break;
            case "Admin":
                view.enterAdminApp();
                break;
            default:
                view.setResultText("Invalid user type.");
                break;
        }
    }
}
