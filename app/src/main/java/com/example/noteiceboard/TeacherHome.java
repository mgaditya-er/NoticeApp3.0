package com.example.noteiceboard;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.navigation.NavigationView;


public class TeacherHome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_home);
        Toolbar toolbar = findViewById(R.id.toolbar); //Ignore red line errors
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ViewGroup layout = (ViewGroup) getLayoutInflater().inflate(R.layout.nav_header, null);
        TextView navName = layout.findViewById(R.id.navName);
        TextView navEmail= layout.findViewById(R.id.navEmail);


// Set the text of the TextView
        String email = getIntent().getStringExtra("email");
        String password = getIntent().getStringExtra("password");
        navName.setText("Your text here");
        navEmail.setText(email);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav,
                R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }



    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                break;
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
                Intent intent1 = getIntent();
                String emailUser = intent1.getStringExtra("email");
                String passwordUser = intent1.getStringExtra("password");
                Intent intent = new Intent(TeacherHome.this, ProfileActivity.class);
                intent.putExtra("email", emailUser);
                intent.putExtra("password", passwordUser);
                startActivity(intent);
                break;
            case R.id.nav_studentlist:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new StudentlistFragment()).commit();
                break;
            case R.id.nav_batches:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                Intent intent2 = new Intent(TeacherHome.this, Batchches.class);
                startActivity(intent2);
                break;
            case R.id.nav_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SettingsFragment()).commit();
                break;
            case R.id.nav_about:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AboutFragment()).commit();
                break;
            case R.id.nav_logout:
                Toast.makeText(this, "Logout!", Toast.LENGTH_SHORT).show();
                // Find the log-out button in the layout
                Button logOutButton = findViewById(R.id.nav_logout);

// Set an OnClickListener to the log-out button
                logOutButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Perform the log-out action here
                        // In this example, we'll just finish the current activity
                        Intent intent = new Intent(TeacherHome.this, SignUpActivity.class);

                        finish();
                    }
                });

                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}