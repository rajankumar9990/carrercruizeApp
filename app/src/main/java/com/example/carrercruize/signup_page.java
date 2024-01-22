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
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;

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
                checkIfUserExists (email.getText ().toString ());

            }
        });
    }
    private void checkIfUserExists(String email) {
        // Use FirebaseAuth to check if a user with the given email exists
        mAuth.fetchSignInMethodsForEmail(email)
                .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                        if (task.isSuccessful()) {
                            SignInMethodQueryResult result = task.getResult();
                            if (result != null && result.getSignInMethods() != null
                                    && !result.getSignInMethods().isEmpty()) {
                                // User with this email already exists
                                Toast.makeText (signup_page.this, "User with this email already exists", Toast.LENGTH_SHORT).show ( );
                            } else {
                                createuser ();
                            }
                        } else {
                            // An error occurred while checking for user existence
                            Toast.makeText (signup_page.this, "Error!", Toast.LENGTH_SHORT).show ( );
                        }
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
        }else if(TextUtils.isEmpty (passop) | passop.length ()<=6){
            pass.setError ("password length can't be <7");
            pass.requestFocus ();
        }else if(TextUtils.isEmpty (confpassop) ){
            confirmpass.setError ("can't be empty");
            confirmpass.requestFocus ();
        }else if(!passop.equals (confpassop)){
            Toast.makeText (this, "Passwords don't match", Toast.LENGTH_SHORT).show ( );
        }
        else{
            FirebaseUser currentUser=mAuth.getCurrentUser ();
            if(currentUser!=null){
                AuthCredential emailCredential = EmailAuthProvider.getCredential(emailop,passop);
                currentUser.linkWithCredential (emailCredential).addOnCompleteListener (new OnCompleteListener<AuthResult> ( ) {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Email credential successfully linked to the account
                            Intent intent=new Intent ( signup_page.this,profilecreation.class );
                            intent.putExtra ("email",emailop);
                            intent.putExtra ("mobile",currentUser.getPhoneNumber ());
                            startActivity (intent);
                            Toast.makeText(signup_page.this, "Email Linked Successfully", Toast.LENGTH_SHORT).show();
                            finish ();
                        } else {
                            // If linking fails, display a message to the user.
                            Toast.makeText(signup_page.this, "Linking Email Failed...\nTry with different credentials...", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }else {
                Toast.makeText (this, "Please verify Mobile No.", Toast.LENGTH_SHORT).show ( );
                startActivity (new Intent ( signup_page.this,verifyOtp.class ));
            }
//            mAuth.createUserWithEmailAndPassword (emailop,passop).addOnCompleteListener (new OnCompleteListener<AuthResult> ( ) {
//                @Override
//                public void onComplete(@NonNull Task<AuthResult> task) {
//                    if(task.isSuccessful ()){
//
//                        Toast.makeText (signup_page.this, "User Account Created!", Toast.LENGTH_SHORT).show ( );
//
//                    }else{
//                        Toast.makeText (signup_page.this, "Registration Error!", Toast.LENGTH_SHORT).show ( );
//                    }
//                }
//            });
        }
    }
}