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
    public void checkDB(String email, String password) {
        if (email.equals("") || password.equals("")){
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
        if (userType.equals("Student")){
            FirebaseMessaging.getInstance().subscribeToTopic("notifications")
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            String msg = "Subscribed to notifications";
                            if (!task.isSuccessful()) {
                                msg = "Subscribe failed";
                            }
                            Log.d(TAG, msg);
                            Toast.makeText(view.getActivity(), msg, Toast.LENGTH_SHORT).show();
                        }
                    });
            view.enterStudentApp();
        }
        else if (userType.equals("Admin")){
            view.enterAdminApp();
        }
        else {
            view.setResultText("Invalid user type.");
        }
    }
}
