package com.example.b07project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.b07project.login.LoginView;
import com.example.b07project.signup.SignUpFragment;
import com.google.firebase.auth.FirebaseAuth;

public class WelcomeActivity extends AppCompatActivity {

    Button goLogin;
    Button goSignup;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome);

        goLogin = findViewById(R.id.login);
        goSignup = findViewById(R.id.signup);
        goLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*goLogin.setVisibility(View.GONE);
                goSignup.setVisibility(View.GONE);
                 */
                Fragment loginFragment = new LoginView();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.welcomeContainer, loginFragment).commit();
            }
        });
        goSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                goLogin.setVisibility(View.GONE);
                goSignup.setVisibility(View.GONE);
                 */
                Fragment signupFragment = new SignUpFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.welcomeContainer, signupFragment).commit();
            }
        });
    }
}