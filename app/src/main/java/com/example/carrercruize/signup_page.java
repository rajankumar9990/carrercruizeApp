package com.example.carrercruize;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class signup_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);
        Button signupbtn=findViewById (R.id.signupButton);
        signupbtn.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                Toast.makeText (signup_page.this, "Complete Next Step to finish Signup!!", Toast.LENGTH_SHORT).show ( );
                startActivity (new Intent (signup_page.this,profilecreation.class));
            }
        });
    }
}