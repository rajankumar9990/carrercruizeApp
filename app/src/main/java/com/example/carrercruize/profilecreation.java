package com.example.carrercruize;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputEditText;

public class profilecreation extends AppCompatActivity {
    TextInputEditText nameview;
    TextInputEditText emailview;
    TextInputEditText ageview;
    TextInputEditText titleview;
    TextInputEditText experienceview;
    TextInputEditText skillsview;
    TextInputEditText bioview;
    Button savebtn;

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
                Toast.makeText (profilecreation.this, "Signup Completed!!!", Toast.LENGTH_SHORT).show ( );
                startActivity (new Intent ( profilecreation.this,dashboard.class ));
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