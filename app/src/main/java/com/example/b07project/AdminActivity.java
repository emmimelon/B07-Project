package com.example.b07project;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.b07project.databinding.ActivityAdminBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
public class AdminActivity extends AppCompatActivity {

    private ActivityAdminBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            String currentName = currentUser.getDisplayName();
            Toast.makeText(getApplicationContext(), "Welcome " + currentName + "!",
                    Toast.LENGTH_SHORT).show();
        }

        BottomNavigationView adminNavView = findViewById(R.id.admin_nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        Toolbar myToolbar = findViewById(R.id.admin_toolbar);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_admin);
        NavController navController = navHostFragment.getNavController();
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        NavigationUI.setupWithNavController(binding.adminNavView, navController);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logout){
            if (mAuth.getCurrentUser() != null){
                mAuth.getCurrentUser().reload();
            }
            startActivity(new Intent(this, WelcomeActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}