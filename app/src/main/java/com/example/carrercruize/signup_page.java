package com.example.carrercruize;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class signup_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);
        TextInputEditText email=findViewById(R.id.emailEditText);
        TextInputEditText pass=findViewById(R.id.passwordEditText1);
        TextInputEditText confirmpass=findViewById(R.id.confirmPasswordEditText);
        Button signupbtn=findViewById (R.id.signupButton);
        signupbtn.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                if (email.getText().length()!=0){
                    if(pass.getText().length()!=0 & confirmpass.getText().length()!=0){
                        if(pass.getText().toString().equals(confirmpass.getText().toString())){
                            Toast.makeText (signup_page.this, "Complete Next Step to finish Signup!!", Toast.LENGTH_SHORT).show ( );
                            startActivity (new Intent (signup_page.this,profilecreation.class));
                        }else{
                            Toast.makeText(signup_page.this, "password doesn't match", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(signup_page.this, "All fields are mandatory!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(signup_page.this, "All fields are mandatory!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}