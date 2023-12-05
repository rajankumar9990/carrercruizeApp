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

public class profilecreation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_profilecreation);

        // Apply fade-in animation to TextView elements
        applyFadeInAnimation(R.id.textViewName);
        applyFadeInAnimation(R.id.textViewEmail);
        applyFadeInAnimation(R.id.textViewAge);
        applyFadeInAnimation(R.id.textViewJobTitle);
        applyFadeInAnimation(R.id.textViewExperience);
        applyFadeInAnimation(R.id.textViewEducation);
        applyFadeInAnimation(R.id.textViewSkills);
        applyFadeInAnimation(R.id.textViewBio);
        Button save=findViewById (R.id.buttonSave);
        save.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                Toast.makeText (profilecreation.this, "Signup Completed!!!", Toast.LENGTH_SHORT).show ( );
                startActivity (new Intent ( profilecreation.this,dashboard.class ));
                applyScaleUpAnimationToButton (R.id.buttonSave);
            }
        });
    }
    private void applyFadeInAnimation(int viewId) {
        TextView textView = findViewById(viewId);
        Animation fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        textView.startAnimation(fadeInAnimation);
    }
    private void applyScaleUpAnimationToButton(int buttonId) {
        Button saveButton = findViewById(buttonId);
        Animation scaleUpAnimation = AnimationUtils.loadAnimation(this, R.anim.scaleup_bottom);
        saveButton.startAnimation(scaleUpAnimation);
    }
}