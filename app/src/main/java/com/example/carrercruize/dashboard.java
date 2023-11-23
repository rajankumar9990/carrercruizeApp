package com.example.carrercruize;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Remove the title from the Toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        ImageView menuIcon = findViewById(R.id.menu_icon);
        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v);
            }
        });

        //bottom navigation
        BottomNavigationView bottomNavigationView=findViewById (R.id.bottomNavigationView);
        home_fragment homeFragment=new home_fragment ();
        job_fragment jobFragment=new job_fragment ();
        profile_fragment profileFragment=new profile_fragment ();
        getSupportFragmentManager ().beginTransaction ().replace (R.id.container,homeFragment).commit ();

        bottomNavigationView.setOnItemSelectedListener (new NavigationBarView.OnItemSelectedListener ( ) {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId ()){
                    case R.id.homenavigate:
                        getSupportFragmentManager ().beginTransaction ().replace (R.id.container,homeFragment).commit ();
                        return true;
                    case R.id.jobnavigate:
                        getSupportFragmentManager ().beginTransaction ().replace (R.id.container,jobFragment).commit ();
                        return true;
                    case R.id.profilenavigate:
                        getSupportFragmentManager ().beginTransaction ().replace (R.id.container,profileFragment).commit ();
                        return true;
                }
                return false;
            }
        });
    }
    private void showPopupMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(this, v);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.menu2, popupMenu.getMenu());

        // Set the click listener for each menu item
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch(item.getItemId ()){
                    default:
                        return false;
                }
            }
        });

        popupMenu.show();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}