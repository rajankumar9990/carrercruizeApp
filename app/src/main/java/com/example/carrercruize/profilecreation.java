package com.example.carrercruize;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class profilecreation extends AppCompatActivity {
    TextInputEditText nameview;
    TextInputEditText emailview;
    TextInputEditText ageview;
    TextInputEditText titleview;
    TextInputEditText experienceview;
    TextInputEditText skillsview;
    TextInputEditText bioview;
    Button savebtn;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Intent intent;
    String reEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_profilecreation);

        nameview=findViewById (R.id.profilename);
        emailview=findViewById (R.id.profileemail);
        ageview=findViewById (R.id.profileage);
        titleview=findViewById (R.id.profilejobtitle);
        experienceview=findViewById (R.id.profileexperience);
        skillsview=findViewById (R.id.profileskills);
        bioview=findViewById (R.id.profilebio);
        savebtn=findViewById (R.id.buttonSave);
        intent=getIntent ();
        reEmail=intent.getStringExtra ("email");
        databaseReference=FirebaseDatabase.getInstance ().getReference().child ("userProfile");
        // Apply fade-in animation to TextView elements
        applyFadeInAnimation(R.id.profilename);
        applyFadeInAnimation(R.id.profileemail);
        applyFadeInAnimation(R.id.profileage);
        applyFadeInAnimation(R.id.profilejobtitle);
        applyFadeInAnimation(R.id.profileexperience);
        applyFadeInAnimation(R.id.profileskills);
        applyFadeInAnimation(R.id.profilebio);
        savebtn.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                String namevalue=nameview.getText ().toString ();
                String emailvalue=emailview.getText ().toString ();
                String agevalue=ageview.getText ().toString ();
                String titlevalue=titleview.getText ().toString ();
                String expvalue=experienceview.getText ().toString ();
                String skillvalue=skillsview.getText ().toString ();
                String biovalue=bioview.getText ().toString ();
                if(TextUtils.isEmpty (namevalue)){
                    nameview.setError ("can't be null");
                    nameview.requestFocus ();
                }else if(TextUtils.isEmpty (emailvalue)){
                    emailview.setError ("can't be null");
                    emailview.requestFocus ();
                }else if(TextUtils.isEmpty (agevalue)){
                    ageview.setError ("can't be null");
                    ageview.requestFocus ();
                }else if(TextUtils.isEmpty (titlevalue)){
                    titleview.setError ("can't be null");
                    titleview.requestFocus ();
                }else if(TextUtils.isEmpty (expvalue)){
                    experienceview.setError ("can't be null");
                    experienceview.requestFocus ();
                }else if(TextUtils.isEmpty (skillvalue)){
                    skillsview.setError ("can't be null");
                    skillsview.requestFocus ();
                }else if(TextUtils.isEmpty (biovalue)){
                    bioview.setError ("can't be null");
                    bioview.requestFocus ();
                }else{
                    userProfile userProfile1=new userProfile ();
                    userProfile1.setAgevalue (agevalue);
                    userProfile1.setBiovalue (biovalue);
                    userProfile1.setEmailvalue (emailvalue);
                    userProfile1.setNamevalue (namevalue);
                    userProfile1.setSkillvalue (skillvalue);
                    userProfile1.setTitlevalue (titlevalue);
                    userProfile1.setExpvalue (expvalue);
                    FirebaseUser currentUser =FirebaseAuth.getInstance ().getCurrentUser ();
                    assert currentUser != null;
                    databaseReference.child (currentUser.getUid ()).setValue (userProfile1).addOnCompleteListener (new OnCompleteListener<Void> ( ) {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText (profilecreation.this, "Signup Completed!!!", Toast.LENGTH_SHORT).show ( );
                            startActivity (new Intent ( profilecreation.this,dashboard.class ));
                            finish ();
                        }
                    }).addOnFailureListener (new OnFailureListener ( ) {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText (profilecreation.this, "Process not completed!\n Close the app and restart", Toast.LENGTH_SHORT).show ( );
                            finishAffinity ();
                        }
                    });

                }

            }
        });
    }
    private void applyFadeInAnimation(int viewId) {
        TextView textView = findViewById(viewId);
        Animation fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        textView.startAnimation(fadeInAnimation);
    }
    private void applySlideInAnimationToButton(int buttonId) {
        TextView saveButton = findViewById(buttonId);
        Animation scaleUpAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_in);
        saveButton.startAnimation(scaleUpAnimation);
    }

}