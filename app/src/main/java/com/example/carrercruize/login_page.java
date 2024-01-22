package com.example.carrercruize;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class login_page extends AppCompatActivity {
    FirebaseAuth mAuth;
    TextInputEditText username;
    TextInputEditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        Button loginbtn=findViewById(R.id.loginbtn1);
        username=findViewById(R.id.usernameEditText);
        password=findViewById(R.id.passwordEditText);
        mAuth=FirebaseAuth.getInstance ();
        FirebaseUser currentuser=mAuth.getCurrentUser ();
        if(currentuser!=null){
            startActivity (new Intent ( login_page.this,dashboard.class ));
        }

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginuser();
            }
        });
    }
    private void loginuser(){
        String email=username.getText ().toString ();
        String passw=password.getText ().toString ();
        if(TextUtils.isEmpty (email)){
            username.setError ("Email can't be null");
            username.requestFocus ();
        }
        else if(TextUtils.isEmpty (passw)){
            password.setError ("password can't be null");
            password.requestFocus ();
        }else{
            mAuth.signInWithEmailAndPassword (email,passw).addOnCompleteListener (new OnCompleteListener<AuthResult> ( ) {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful ()){
                        Toast.makeText (login_page.this, "user logged in successfully!", Toast.LENGTH_SHORT).show ( );
                        startActivity (new Intent ( login_page.this,dashboard.class ));
                    }else{
                        Toast.makeText (login_page.this, "Registration error!", Toast.LENGTH_SHORT).show ( );
                    }
                }
            });
        }
    }

}