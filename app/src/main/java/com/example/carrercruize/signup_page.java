package com.example.carrercruize;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signup_page extends AppCompatActivity {
    FirebaseAuth mAuth;
    TextInputEditText email;
    TextInputEditText pass;
    TextInputEditText confirmpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);
        TextView loginbtn=findViewById (R.id.textView7);
        email=findViewById(R.id.emailEditText);
        pass=findViewById(R.id.passwordEditText1);
        confirmpass=findViewById(R.id.confirmPasswordEditText);
        Button signupbtn=findViewById (R.id.signupButton);
        mAuth= FirebaseAuth.getInstance ();
        //login button
        loginbtn.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {
                startActivity (new Intent ( signup_page.this,login_page.class ));
            }
        });
        signupbtn.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                createuser();

            }
        });
    }
    private void createuser(){
        String emailop=email.getText ().toString ();
        String passop=pass.getText ().toString ();
        String confpassop=confirmpass.getText ().toString ();
        if(TextUtils.isEmpty (emailop)){
            email.setError ("email can't be empty");
            email.requestFocus ();
        }else if(TextUtils.isEmpty (passop)){
            pass.setError ("password can't be empty");
            pass.requestFocus ();
        }else if(TextUtils.isEmpty (confpassop)){
            confirmpass.setError ("can't be empty");
            confirmpass.requestFocus ();
        }else if(!passop.equals (confpassop)){
            Toast.makeText (this, "Passwords don't match", Toast.LENGTH_SHORT).show ( );
        }
        else{
            mAuth.createUserWithEmailAndPassword (emailop,passop).addOnCompleteListener (new OnCompleteListener<AuthResult> ( ) {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful ()){
                        Intent intent=new Intent ( signup_page.this,profilecreation.class );
                        intent.putExtra ("email",emailop);
                        startActivity (intent);
                        Toast.makeText (signup_page.this, "User Account Created!", Toast.LENGTH_SHORT).show ( );

                    }else{
                        Toast.makeText (signup_page.this, "Registration Error!", Toast.LENGTH_SHORT).show ( );
                    }
                }
            });
        }
    }
}