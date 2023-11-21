package com.example.b07project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.b07project.databinding.FragmentComplaintsBinding;
import com.example.b07project.ui.complaints.ComplaintsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavArgument;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.b07project.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        String user = intent.getStringExtra("UTORid");
        Toast.makeText(getApplicationContext(), "Signed in as: " + user,
                Toast.LENGTH_SHORT).show();


        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_complaints, R.id.navigation_events, R.id.navigation_notifications, R.id.navigation_post)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);

        //THIS PASSES THE UTORID TO COMPLAINTS, DO THE SAME SETUP WITH YOURS IF YOU NEED IT :)))
        navController.getGraph().findNode(R.id.navigation_complaints)
                .addArgument("UTORid", new NavArgument.Builder()
                        .setDefaultValue(user)
                        .build());

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

    }

}