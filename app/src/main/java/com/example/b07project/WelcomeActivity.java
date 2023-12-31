package com.example.b07project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.b07project.databinding.ActivityWelcomeBinding;
import com.example.b07project.login.LoginView;
import com.example.b07project.signup.SignUpFragment;

public class WelcomeActivity extends AppCompatActivity {
    private ActivityWelcomeBinding binding;
    Button goLogin;
    Button goSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityWelcomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        goLogin = findViewById(R.id.login);
        goSignup = findViewById(R.id.signup);
        goLogin.setVisibility(View.VISIBLE);
        goSignup.setVisibility(View.VISIBLE);
        goLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goLogin.setVisibility(View.GONE);
                goSignup.setVisibility(View.GONE);
                Fragment loginFragment = new LoginView();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.welcomeContainer, loginFragment).commit();
            }
        });
        goSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goLogin.setVisibility(View.GONE);
                goSignup.setVisibility(View.GONE);
                Fragment signupFragment = new SignUpFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.welcomeContainer, signupFragment).commit();
            }
        });
    }
}