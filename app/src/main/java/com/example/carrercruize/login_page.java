package com.example.carrercruize;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class login_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        Button loginbtn=findViewById(R.id.loginbtn1);
        TextInputEditText username=findViewById(R.id.usernameEditText);
        TextInputEditText password=findViewById(R.id.passwordEditText1);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(username.getText().toString().length()!=0 & password.getText().toString().length()!=0 ){
                    startActivity(new Intent(login_page.this,dashboard.class));
                }
                else{
                    Toast.makeText(login_page.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}