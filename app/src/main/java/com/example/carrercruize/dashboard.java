package com.example.carrercruize;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class dashboard extends AppCompatActivity {
    GoogleSignInClient mgooglesigninclient;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        mAuth=FirebaseAuth.getInstance ();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Remove the title from the Toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow ();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(0xFF04147A);
        }

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mgooglesigninclient=GoogleSignIn.getClient (this,gso);
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
                    case R.id.signoutapp:
                        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext ());
                        FirebaseUser currentuser=mAuth.getCurrentUser ();
                        if (acct != null) {
                            signOut();
                        }else if(currentuser!=null){
                            mAuth.signOut ();
                            startActivity (new Intent ( dashboard.this,MainActivity.class ));
                            Toast.makeText (dashboard.this, "logged out sucessfully!", Toast.LENGTH_SHORT).show ( );
                            finish ();
                        }else{
                            Toast.makeText (dashboard.this, "You are not logged in!", Toast.LENGTH_SHORT).show ( );
                        }
                        return true;

                }
                return false;
            }
        });

        popupMenu.show();
    }

    private void signOut() {
        mgooglesigninclient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void> () {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful ()){
                            Toast.makeText (dashboard.this, "Logged Out!", Toast.LENGTH_SHORT).show ( );
                            startActivity (new Intent ( dashboard.this,MainActivity.class ));
                            finish ();
                        }else{
                            Toast.makeText (dashboard.this, "Unexceptional Error!", Toast.LENGTH_SHORT).show ( );
                        }

                    }
                });
    }
    private void setStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

        }
    }

}