package com.example.b07project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.b07project.R;
import com.example.b07project.login.LoginFragment;
import com.example.b07project.signup.SignUpFragment;

public class WelcomeActivity extends AppCompatActivity {

    Button goLogin;
    Button goSignup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent Logged = getIntent();
        if (Logged.getStringExtra("studentUTORid") != null){
            Intent startStu = new Intent(WelcomeActivity.this, MainActivity.class);
            String user = Logged.getStringExtra("studentUTORid");
            startStu.putExtra("UTORid", user);
            startActivity(startStu);
        }
        else if (Logged.getStringExtra("adminUTORid") != null){
            Intent startAdm = new Intent(WelcomeActivity.this, AdminActivity.class);
            String user = Logged.getStringExtra("adminUTORid");
            startAdm.putExtra("UTORid", user);
            startActivity(startAdm);
        }

        setContentView(R.layout.activity_welcome);

        goLogin = findViewById(R.id.login);
        goSignup = findViewById(R.id.signup);
        goLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*goLogin.setVisibility(View.GONE);
                goSignup.setVisibility(View.GONE);
                 */
                Fragment loginFragment = new LoginFragment();
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